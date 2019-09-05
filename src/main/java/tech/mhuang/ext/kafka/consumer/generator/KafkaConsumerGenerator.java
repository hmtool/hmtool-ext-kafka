package tech.mhuang.ext.kafka.consumer.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import tech.mhuang.ext.kafka.admin.event.KafkaEvent;
import tech.mhuang.ext.kafka.consumer.bean.ConsumerBean;
import tech.mhuang.core.util.CollectionUtil;
import tech.mhuang.core.util.StringUtil;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消费者生成器
 *
 * @author mhuang
 * @since 1.0.0
 */
public final class KafkaConsumerGenerator {


    /**
     * 生成消费者参数
     *
     * @param consumerBean 消费者的bean
     */
    public static List generatorParam(ConsumerBean consumerBean) {
        List result = new ArrayList();
        Map<String, Object> map = ((JSONObject) JSON.toJSON(consumerBean)).getInnerMap();
        String[] topics = StringUtil.split(consumerBean.getTopics(), ",");
        if (CollectionUtil.isNotEmpty(topics)) {
            for (String topic : topics) {
                initTopic(map, topic, consumerBean, result);
            }
        }
        return result;
    }

    /**
     * 根据主题初始化
     *
     * @param props        初始化的参数
     * @param topic        初始化的主题
     * @param consumerBean 初始化
     */
    private static void initTopic(Map<String, Object> props, String topic, ConsumerBean consumerBean, List result) {
        Map<String, Object> cloneProps = new HashMap<>(props);
        KafkaConsumer<Object, Object> consumer = new KafkaConsumer<>(props);
        List<PartitionInfo> partitionList = consumer.partitionsFor(topic);
        consumer.close();
        Integer threadPartionNum = consumerBean.getThreadPartitionNum();
        List<TopicPartition> operaParttionList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int crtParitionCount = partitionList.size();
        //如果设置的分片数大于当前的分片数
        if (threadPartionNum > crtParitionCount) {
            //放入一个线程处理
            threadPartionNum = crtParitionCount;
        }
        for (int i = 0; i < crtParitionCount; i++) {
            int partition = partitionList.get(i).partition();
            sb.append("|").append(partition);
            operaParttionList.add(new TopicPartition(topic, partition));
            //解决除数为0的情况下
            if (crtParitionCount - 1 == i || (threadPartionNum - 1) % i == 0) {
                cloneProps.put("group.id", sb.insert(0, "-group-").insert(0, topic).toString());
                result.add(initPartition(cloneProps, operaParttionList, topic, consumerBean));
                operaParttionList = new ArrayList<>();
                sb = new StringBuilder();
            }
        }
    }

    private static JSONObject initPartition(Map<String, Object> props, List<TopicPartition> partitions,
                                            String topic, ConsumerBean consumerBean) {
        JSONObject params = new JSONObject(8);
        params.put("action", KafkaEvent.CREATE);
        params.put("consumerMap", props);
        params.put("consumerBean", consumerBean);
        params.put("partition", partitions);
        params.put("topic", topic);
        return params;
    }
}
