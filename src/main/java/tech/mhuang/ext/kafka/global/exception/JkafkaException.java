package tech.mhuang.ext.kafka.global.exception;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * Kafka异常类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class JkafkaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 信息
     */
    @Setter
    @Getter
    private String message;

    /**
     * 异常
     */
    @Setter
    @Getter
    private Throwable cause;

    public JkafkaException(String message) {
        super(message);
        this.message = message;
    }

    public JkafkaException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }
}
