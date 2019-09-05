package tech.mhuang.ext.kafka.consumer.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * kafka回调的实体类
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaCallbackBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String topic;

    private Long offset;

    private Exception e;
}
