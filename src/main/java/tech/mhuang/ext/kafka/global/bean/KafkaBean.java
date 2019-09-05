package tech.mhuang.ext.kafka.global.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * kafka通用配置
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
public class KafkaBean {

    @JSONField(name = "bootstrap.servers")
    private String servers;
}
