package tech.mhuang.ext.kafka.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import tech.mhuang.ext.kafka.admin.bean.KafkaInfo;
import tech.mhuang.ext.kafka.admin.external.IKafkaConsumer;
import tech.mhuang.ext.kafka.admin.external.IKafkaExternal;
import tech.mhuang.ext.kafka.admin.external.IKafkaProducer;
import tech.mhuang.ext.kafka.consumer.bean.ConsumerBean;
import tech.mhuang.ext.kafka.consumer.generator.KafkaConsumerGenerator;
import tech.mhuang.ext.kafka.global.exception.JkafkaException;
import tech.mhuang.ext.kafka.producer.bean.ProducerBean;
import tech.mhuang.core.check.CheckAssert;
import tech.mhuang.core.pool.BaseExecutor;
import tech.mhuang.core.pool.DefaultThreadPool;
import tech.mhuang.core.util.CollectionUtil;
import tech.mhuang.core.util.StringUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * kafka的信息
 *
 * @author mhuang
 * @since 1.0.0
 */
@Slf4j
public class KafkaFramework {

    /**
     * kafka全部配置
     */
    @Getter
    private KafkaInfo info;

    /**
     * 启动成功的kafka生产者（可用于获取变更等进行处理）
     */
    @Getter
    private Map<String, IKafkaProducer> successProducerMap = new ConcurrentHashMap<>();

    /**
     * 启动成功的kafka消费者（可用于获取变更等进行处理）
     */
    @Getter
    private Map<String, IKafkaConsumer> successConsumerMap = new ConcurrentHashMap<>();

    /**
     * 线程池（消费者使用）
     */
    @Getter
    private BaseExecutor executorService;

    /**
     * 提供kafka生产、消费bean扩展（可自定义生产、消费处理等方式）
     */
    @Getter
    private IKafkaExternal kafkaExternal;

    public KafkaFramework(KafkaInfo info) {
        this.info = info;
    }


    /**
     * 自己构建的线程池
     *
     * @param executorService 线程池
     * @return 返回kafka框架
     */
    public KafkaFramework executorService(BaseExecutor executorService) {
        this.executorService = executorService;
        return this;
    }

    /**
     * 自行创建时将继承的扩展接口
     *
     * @param kafkaExternal 扩展接口
     * @return 返回kafka框架
     */
    public KafkaFramework kafkaExternal(IKafkaExternal kafkaExternal) {
        this.kafkaExternal = kafkaExternal;
        return this;
    }

    /**
     * 启动
     */
    public void start() {
        log.debug("loading kafka client....");
        if (kafkaExternal == null) {
            kafkaExternal = new IKafkaExternal() {
            };
        }
        if (executorService == null) {
            executorService = new DefaultThreadPool();
        }
        //生产者启动
        if (info.isEnableProducer()) {
            if (CollectionUtil.isEmpty(info.getProducerMap())) {
                throw new JkafkaException("生产者启动但没有配置对应生产者,...");
            }
            info.getProducerMap().forEach((key, value) -> {
                if(value.isEnable()){
                    generatorPro(key, value);
                }
            });
        }
        //消费者启动
        if (info.isEnableConsumer()) {
            if (CollectionUtil.isEmpty(info.getConsumerMap())) {
                throw new JkafkaException("消费者启动但没有配置对应消费者,...");
            }
            info.getConsumerMap().forEach((key, value) -> {
                if(value.isEnable()){
                    generatorCon(key, value);
                }
            });
        }
        log.debug("start kafka client success..");
    }

    /**
     * 构建生产者
     *
     * @param key
     * @param value
     */
    private void generatorPro(String key, ProducerBean value) {
        if (StringUtil.isEmpty(value.getServers())) {
            CheckAssert.check(info.getServers(), String.format("生产者%s:没有绑定对应的属性", key));
            value.setServers(info.getServers());
        }

        IKafkaProducer kafkaJProducer = kafkaExternal.createProducer(key);
        kafkaJProducer.execute(JSON.toJSON(value));
        successProducerMap.put(key, kafkaJProducer);
    }

    /**
     * 构建消费者
     *
     * @param key
     * @param value
     */
    private void generatorCon(String key, ConsumerBean value) {
        if (StringUtil.isEmpty(value.getServers())) {
            CheckAssert.check(info.getServers(), String.format("消费者%s:没有绑定对应实例", key));
            value.setServers(info.getServers());
        }
        CheckAssert.check(value.getTopics(), String.format("消费者%s:没有指定对应主题", key));
        CheckAssert.check(value.getInvokeBeanName(), String.format("消费者%s:没有指定对应的调用bean", key));
        CheckAssert.check(value.getInvokeMethodName(), String.format("消费者%s:没有指定对应的方法名", key));
        List<JSONObject> partionParamList = KafkaConsumerGenerator.generatorParam(value);
        if (CollectionUtil.isNotEmpty(partionParamList)) {
            partionParamList.stream().forEach(partition -> {
                String createKey = partition.getJSONObject("consumerMap").getString("group.id");
                IKafkaConsumer kafkaConsumer = kafkaExternal.createConsumer(createKey);
                kafkaConsumer.execute(partition);
                executorService.submit(kafkaConsumer);
                successConsumerMap.put(createKey, kafkaConsumer);
            });
        }
    }
}
