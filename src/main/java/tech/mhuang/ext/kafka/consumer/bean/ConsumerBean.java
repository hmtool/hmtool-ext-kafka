package tech.mhuang.ext.kafka.consumer.bean;

import com.alibaba.fastjson.annotation.JSONField;
import tech.mhuang.ext.kafka.global.bean.KafkaBean;
import tech.mhuang.ext.kafka.global.constans.KafkaGlobal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消费者配置类
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ConsumerBean extends KafkaBean {

    /**
     * 建的反序列化
     */
    @JSONField(name = "key.deserializer")
    private String keyDeserializer;

    /**
     * 值的反序列化
     */
    @JSONField(name = "value.deserializer")
    private String valueDeserializer;

    /**
     * 执行的bean名称
     */
    @JSONField(serialize = false, name = "invoke.bean")
    private String invokeBeanName;

    /**
     * 执行的方法名
     */
    @JSONField(serialize = false, name = "invoke.method")
    private String invokeMethodName;

    /**
     * 执行回调的方法名
     */
    @JSONField(serialize = false, name = "invoke.callback")
    private String invokeCallback;

    /**
     * 主题
     */
    @JSONField(serialize = false, name = "topics")
    private String topics;

    /**
     * 分组id
     */
    @JSONField(serialize = false, name = "group.id")
    private String groupId;

    /**
     * 拉取的间隔
     */
    @JSONField(serialize = false)
    private Integer pull;

    /**
     * 线程数
     */
    @JSONField(serialize = false, name = "thread.partition.num")
    private Integer threadPartitionNum;

    /**
     * 是否自动提交
     */
    @JSONField(name = "enable.auto.commit")
    private Boolean enableAutoCommit;

    /**
     * 自动提交的毫秒数
     */
    @JSONField(name = "auto.commit.interval.ms")
    private Integer autoCommitIntervalMs;

    /**
     * session超时的毫秒数
     */
    @JSONField(name = "session.timeout.ms")
    private Integer sessionTimeOutMs;

    /**
     * 自动偏移重置
     */
    @JSONField(name = "auto.offset.reset")
    private String autoOffsetReset;

    public ConsumerBean() {
        this.keyDeserializer = KafkaGlobal.FIELD_KEY_DESERIALIZER_DEFAULT;
        this.valueDeserializer = KafkaGlobal.FIELD_VALUE_DESERIALIZERDEFAULT;
        this.pull = KafkaGlobal.FIELD_CONSUMER_PULL_DEFAULT;
        this.threadPartitionNum = KafkaGlobal.FIELD_CONSUMER_THREAD_PARTITION_DEFAULT;
        this.enableAutoCommit = KafkaGlobal.FIELD_CONSUMER_ENABLE_AUTO_COMMIT_DEFAULT;
        this.autoCommitIntervalMs = KafkaGlobal.FIELD_CONSUMER_AUTO_COMMIT_INTERVAL_MS_DEFAULT;
        this.sessionTimeOutMs = KafkaGlobal.FIELD_CONSUMER_SESSION_TIMEOUT_MS_DEFAULT;
        this.autoOffsetReset = KafkaGlobal.FIELD_CONSUMER_AUTO_OFFSET_RESET_DEFAULT;
    }
}
