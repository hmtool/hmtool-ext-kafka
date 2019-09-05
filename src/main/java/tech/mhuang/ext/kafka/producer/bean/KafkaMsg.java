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

    private String topic;

    private Long offset;

    private Object msg;
}
