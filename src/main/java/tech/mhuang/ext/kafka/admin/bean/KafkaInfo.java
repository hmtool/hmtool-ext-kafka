package tech.mhuang.ext.kafka.admin.bean;

import tech.mhuang.ext.kafka.consumer.bean.ConsumerBean;
import tech.mhuang.ext.kafka.producer.bean.ProducerBean;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * kafka相关信息
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class KafkaInfo {

    /**
     * 对应的通用服务
     */
    private String servers;

    /**
     * 对应的序列化key
     */
    private String keySerializer;

    /**
     * 对应的序列化value
     */
    private String valueSerializer;

    /**
     * 对应的反序列化key
     */
    private String keyDeserializer;

    /**
     * 对应的反序列化value
     */
    private String valueDeserializer;

    /**
     * 开启生产者
     */
    private boolean enableProducer;

    /**
     * 开启消费者
     */
    private boolean enableConsumer;

    /**
     * 生产者信息，对应多个或单个
     */
    private Map<String, ProducerBean> producerMap = new HashMap<>();

    /**
     * 消费者信息对应多个或者单个
     */
    private Map<String, ConsumerBean> consumerMap = new HashMap<>();
}
