package tech.mhuang.ext.kafka.producer.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * kafka对象信息
 *
 * @author mhuang
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题
     */
    private String topic;

    /**
     * 偏移量
     */
    private Long offset;

    /**
     * 消息数据
     */
    private Object msg;
}
