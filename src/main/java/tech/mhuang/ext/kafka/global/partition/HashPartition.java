package tech.mhuang.ext.kafka.global.partition;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.Random;


/**
 *
 * Hash分片
 *
 * @author mhuang
 * @since 1.0.0
 */
@Slf4j
public class HashPartition implements Partitioner {

    private Random random = new Random();

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //获取当前配置的分片数
        Integer partitions = cluster.partitionCountForTopic(topic);
        //随机获取分片
        return random.nextInt(partitions);
    }

    @Override
    public void close() {
        log.debug("partition closed...");
    }

}
