package tech.mhuang.ext.kafka.producer.process;

import tech.mhuang.ext.kafka.admin.event.KafkaEvent;
import tech.mhuang.ext.kafka.admin.external.IKafkaProducer;
import tech.mhuang.core.observer.AbstractObServer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 *
 * Kafka生产者
 *
 * @author mhuang
 * @since 1.0.0
 */
public class DefaultKafkaProducer<K, V> extends AbstractObServer<Map> implements IKafkaProducer<K, V, Map> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * kafka producer
     */
    private Producer<K, V> producer;

    /**
     * kafka producer properties
     */
    private Properties prop = new Properties();

    @Override
    public Future<RecordMetadata> send(String topic, K key, V value) {
        logger.debug("正在发送kafka数据-->topic:{},key:{},value:{}", topic, key, value);
        return producer.send(new ProducerRecord<>(topic, key, value));
    }

    @Override
    public Future<RecordMetadata> send(String topic, K key, V value, Callback callBack) {
        logger.debug("正在发送kafka数据-->topic:{},key:{},value:{}", topic, key, value);
        return producer.send(new ProducerRecord<>(topic, key, value), callBack);
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record) {
        return producer.send(record);
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record, Callback callBack) {
        return producer.send(record, callBack);
    }

    @Override
    public void setProperty(String key, Object value) {
        this.prop.put(key, value);
        this.refresh();
    }

    @Override
    public void setProperty(Map<String, Object> map) {
        this.prop.putAll(map);
        this.refresh();
    }


    /**
     * refresh producer config
     */
    private void refresh() {
        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<>(prop);
    }

    final String ACTION = "action";

    /**
     * 观察者发现数据变化执行
     */
    @Override
    protected void execute() {
        if (KafkaEvent.CREATE.equals(super.data.getOrDefault(ACTION, KafkaEvent.CREATE))) {
            this.prop.putAll(super.data);
            this.refresh();
        }
    }
}