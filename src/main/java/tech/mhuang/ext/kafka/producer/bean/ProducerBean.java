package tech.mhuang.ext.kafka.producer.bean;

import com.alibaba.fastjson.annotation.JSONField;
import tech.mhuang.ext.kafka.global.bean.KafkaBean;
import tech.mhuang.ext.kafka.global.constans.KafkaGlobal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * kafka生产者配置
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProducerBean extends KafkaBean {

    /**
     * 是否开启生产者
     */
    @JSONField(serialize = false)
    private boolean enable;

    /**
     * key序列化
     */
    @JSONField(name = "key.serializer")
    private String keySerializer;

    /**
     * value序列化
     */
    @JSONField(name = "value.serializer")
    private String valueSerializer;

    /**
     * 确认方式(默认ALL)
     */
    @JSONField(name = "acks")
    private String acks;

    @JSONField(name = "retries")
    private String retries;

    @JSONField(name = "batch.size")
    private Integer batchSize;

    @JSONField(name = "linger.ms")
    private Integer lingerMs;

    @JSONField(name = "buffer.memory")
    private Long bufferMemory;

    @JSONField(name = "partitioner.class")
    private String partitionerClass;

    public ProducerBean() {

        this.setBatchSize(KafkaGlobal.FIELD_PRODUCER_BATCH_SIZE_DEFAULT);
        this.setAcks(KafkaGlobal.FIELD_PRODUCER_ACKS_DEFAULT);
        this.setValueSerializer(KafkaGlobal.FIELD_VALUE_SERIALIZERDEFAULT);
        this.setKeySerializer(KafkaGlobal.FIELD_KEY_SERIALIZER_DEFAULT);
        this.setRetries(KafkaGlobal.FIELD_PRODUCER_RETRIES_DEFAULT);
        this.setBufferMemory(KafkaGlobal.FIELD_PRODUCER_BUFFER_MEMORY_DEFAULT);
        this.setPartitionerClass(KafkaGlobal.FIELD_PRODUCER_PARTITIONER_CLASS_FEFAULT);
        this.setLingerMs(KafkaGlobal.FIELD_PRODUCER_LINGERMS_DEFAULT);
    }
}
