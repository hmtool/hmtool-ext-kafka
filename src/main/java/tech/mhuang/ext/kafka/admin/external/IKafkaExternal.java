package tech.mhuang.ext.kafka.admin.external;

import tech.mhuang.ext.kafka.consumer.process.DefaultKafkaConsumer;
import tech.mhuang.ext.kafka.producer.process.DefaultKafkaProducer;

/**
 * Kafka扩展服务
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface IKafkaExternal {

    /**
     * 对外提供创建生产者方式.
     *
     * @param key 产生的key
     * @return kafka生产者接口
     */
    default IKafkaProducer createProducer(String key) {
        return new DefaultKafkaProducer();
    }

    /**
     * 对外提供创建消费者方式
     *
     * @param key 产生的key
     * @return kafka消费者接口
     */
    default IKafkaConsumer createConsumer(String key) {
        return new DefaultKafkaConsumer();
    }
}
