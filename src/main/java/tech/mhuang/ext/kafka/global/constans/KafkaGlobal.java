package tech.mhuang.ext.kafka.global.constans;

/**
 *
 * kafka全局常量
 *
 * @author mhuang
 * @since 1.0.0
 */
public class KafkaGlobal {


    //common
    /**
     * key.serializer
     */
    public final static String FIELD_KEY_SERIALIZER_DEFAULT = "org.apache.kafka.common.serialization.StringSerializer";
    public final static String FIELD_KEY_DESERIALIZER_DEFAULT = "org.apache.kafka.common.serialization.StringDeserializer";
    //序列化value默认配置
    /**
     * value.serializer
     */
    public final static String FIELD_VALUE_SERIALIZERDEFAULT = "org.apache.kafka.common.serialization.StringSerializer";
    public final static String FIELD_VALUE_DESERIALIZERDEFAULT = "org.apache.kafka.common.serialization.StringDeserializer";
    ///////////////////////producer
    /**
     * FIELD_PRODUCER_ENABLE
     */
    public final static String FIELD_PRODUCER_ACKS_DEFAULT = "all";
    public final static String FIELD_PRODUCER_RETRIES_DEFAULT = "0";
    public final static Integer FIELD_PRODUCER_BATCH_SIZE_DEFAULT = 16384;
    public final static Integer FIELD_PRODUCER_LINGERMS_DEFAULT = 1;
    public final static Long FIELD_PRODUCER_BUFFER_MEMORY_DEFAULT = 33554432L;
    public final static String FIELD_PRODUCER_PARTITIONER_CLASS_FEFAULT = "tech.mhuang.ext.kafka.global.partition.HashPartition";

    ////consumer
    /**
     * consumer
     */
    public final static Boolean FIELD_CONSUMER_ENABLE_AUTO_COMMIT_DEFAULT = false;
    public final static Integer FIELD_CONSUMER_AUTO_COMMIT_INTERVAL_MS_DEFAULT = 3000;
    public final static Integer FIELD_CONSUMER_SESSION_TIMEOUT_MS_DEFAULT = 30000;
    public final static String FIELD_CONSUMER_AUTO_OFFSET_RESET_DEFAULT = "latest";
    public final static Integer FIELD_CONSUMER_PULL_DEFAULT = 200;
    public final static Integer FIELD_CONSUMER_THREAD_PARTITION_DEFAULT = 1;

    public static final String DEFAULT_SERVERS = "127.0.0.1:9092";
}
