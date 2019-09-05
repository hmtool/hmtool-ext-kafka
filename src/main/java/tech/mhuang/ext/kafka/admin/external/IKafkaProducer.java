package tech.mhuang.ext.kafka.admin.external;

import tech.mhuang.core.observer.BaseObServer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * 接口层--对外公布
 *
 * @param <K> kafka调用的key
 * @param <V> kafka调用的value
 * @param <T> kafka传递的数据对象
 * @author mhuang
 * @since 1.0.0
 */
public interface IKafkaProducer<K, V, T> extends BaseObServer<T> {


    /**
     * 发送
     *
     * @param topic 主题
     * @param key   键
     * @param value 值
     * @return get同步等待获取进行下一步。也可忽略。
     */
    Future<RecordMetadata> send(String topic, K key, V value);

    /**
     * 发送
     *
     * @param topic    主题
     * @param key      键
     * @param value    值
     * @param callBack 不同步的时候回调
     * @return 可get同步等待获取进行下一步。也可忽略。
     */
    Future<RecordMetadata> send(String topic, K key, V value, Callback callBack);


    /**
     * 发送
     *
     * @param record 发送的数据
     * @return 可get同步等待获取进行下一步。也可忽略。
     */
    Future<RecordMetadata> send(ProducerRecord<K, V> record);

    /**
     * 发送
     *
     * @param record   发送的数据
     * @param callBack 回调
     * @return 可get同步等待获取进行下一步。也可忽略。
     */
    Future<RecordMetadata> send(ProducerRecord<K, V> record, Callback callBack);

    /**
     * add or update  setter config
     *
     * @param key   传入的key
     * @param value 设置的值
     */
    void setProperty(String key, Object value);

    /**
     * add or update setter config
     *
     * @param map 设置的map集合
     */
    void setProperty(Map<String, Object> map);
}
