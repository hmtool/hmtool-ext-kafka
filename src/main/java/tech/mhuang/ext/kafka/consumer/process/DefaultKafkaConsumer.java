package tech.mhuang.ext.kafka.consumer.process;

import com.alibaba.fastjson.JSONObject;
import tech.mhuang.ext.kafka.admin.event.KafkaEvent;
import tech.mhuang.ext.kafka.admin.external.IKafkaConsumer;
import tech.mhuang.ext.kafka.consumer.bean.ConsumerBean;
import tech.mhuang.ext.kafka.consumer.bean.KafkaCallbackBean;
import tech.mhuang.ext.kafka.global.exception.JkafkaException;
import tech.mhuang.ext.kafka.producer.bean.KafkaMsg;
import tech.mhuang.core.observer.AbstractObServer;
import tech.mhuang.core.reflect.BaseReflectInvoke;
import tech.mhuang.core.reflect.DefaultReflectInvoke;
import tech.mhuang.core.util.StringUtil;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 消费者线程
 *
 * @author mhuang
 * @since 1.0.0
 */
public class DefaultKafkaConsumer extends AbstractObServer<JSONObject> implements IKafkaConsumer<JSONObject> {

    @Setter
    private String topic;

    @Setter
    private List<TopicPartition> partition;

    @Setter
    private Map<String, Object> consumerMap;

    @Setter
    private ConsumerBean consumerBean;

    private AtomicBoolean running = new AtomicBoolean(true);

    @Setter
    private KafkaConsumer<Object, Object> consumer;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 反射调用器
     */
    private BaseReflectInvoke invoke;

    @Override
    public DefaultKafkaConsumer invoke(BaseReflectInvoke invoke) {
        this.invoke = invoke;
        return this;
    }

    /**
     * process pull
     */
    private void start() {
        try {
            logger.info("start consumer properties:{}", consumerMap);
            consumer = new KafkaConsumer<>(consumerMap);
            consumer.assign(partition);

            //若消费者未指定则使用jdk反射方式
            if (invoke == null) {
                invoke = new DefaultReflectInvoke();
            }
            Boolean isCallBackMethod = StringUtil.isNotEmpty(consumerBean.getInvokeCallback());

            while (running.get()) {
                ConsumerRecords<Object, Object> records = consumer.poll(consumerBean.getPull());

                records.forEach(record -> {
                    logger.info("kafka反馈给系统的数据是:{}", record);
                    KafkaMsg kafkaMsg = new KafkaMsg(record.topic(), record.offset(), record.value());
                    try {
                        invoke.getMethodToValue(consumerBean.getInvokeBeanName(),
                                consumerBean.getInvokeMethodName(), kafkaMsg);
                    } catch (Exception e) {
                        logger.error("消费数据异常", e);
                    }
                });

                if (isCallBackMethod && !records.isEmpty()) {
                    consumer.commitAsync((offsets, ex) -> {
                        List<KafkaCallbackBean> listBean = new ArrayList<>(offsets.size());
                        offsets.forEach((parition, metadata) -> {
                            KafkaCallbackBean callBackBean = new KafkaCallbackBean(
                                    parition.topic(),
                                    metadata.offset(),
                                    ex
                            );
                            listBean.add(callBackBean);
                        });
                        try {
                            invoke.getMethodToValue(
                                    consumerBean.getInvokeBeanName(),
                                    consumerBean.getInvokeCallback(),
                                    listBean
                            );
                        } catch (Exception e) {
                            logger.error("消费确认数据异常", e);
                        }
                    });
                } else {
                    consumer.commitAsync();
                }
            }
        } catch (Exception e) {
            throw new JkafkaException(e.getMessage(), e);
        } finally {
            //避免异常情况、若consumer 为空则是异常
            if (consumer != null) {
                consumer.commitAsync();
                consumer.close();
            }
            logger.info("consumer {} close", topic);
        }
    }

    @Override
    public void run() {
        //use consumer status for start
        start();
    }

    /**
     * 销毁调用
     *
     * @param params 销毁的参数
     */
    private void destroy(Map<String, Object> params) {
        Object destroyPartition = params.get(topic);
        if (destroyPartition != null) {
            logger.info("destory current topic:{} satisfied,params:{} ", topic, params);
            running.compareAndSet(true, false);
            logger.info("destory current topis:{},groupId:{}", topic, consumerMap.get("group.id"));
        }
    }

    private final String ACTION = "action";
    private final String PARAM = "param";

    @Override
    protected void execute() {
        if (KafkaEvent.CREATE == data.getOrDefault(ACTION, KafkaEvent.CREATE)) {
            this.topic = data.getString("topic");
            this.consumerBean = (ConsumerBean) data.get("consumerBean");
            this.partition = (List<TopicPartition>) data.get("partition");
            this.consumerMap = data.getJSONObject("consumerMap");
        }
        if (KafkaEvent.DESTROY == data.get(ACTION)) {
            destroy((Map<String, Object>) data.get(PARAM));
        }
        if (KafkaEvent.PAUSE == data.get(ACTION)) {
            running.compareAndSet(true, false);
        }
        if (KafkaEvent.RESUME == data.get(ACTION)) {
            running.compareAndSet(false, true);
            start();
        }
    }
}
