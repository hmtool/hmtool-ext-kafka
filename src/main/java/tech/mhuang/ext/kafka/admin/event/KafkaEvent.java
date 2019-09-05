package tech.mhuang.ext.kafka.admin.event;

/**
 * kafka操作事件
 *
 * @author mhuang
 * @since 1.0.0
 */
public enum KafkaEvent {
    /**
     * 暂停
     * mut not supported
     */
    PAUSE,
    /**
     * 继续
     * mut not supported
     */
    RESUME,
    /**
     * 销毁
     *
     * @version:
     */
    DESTROY,
    /**
     * 创建
     */
    CREATE
}
