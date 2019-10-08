package tech.mhuang.ext.kafka.admin;

import tech.mhuang.ext.kafka.admin.bean.KafkaInfo;
import tech.mhuang.ext.kafka.consumer.bean.ConsumerBean;
import tech.mhuang.ext.kafka.producer.bean.ProducerBean;
import tech.mhuang.core.builder.BaseBuilder;

/**
 * kafka构建工具
 *
 * @author mhuang
 * @since 1.0.0
 */
public class KafkaBuilder {

    /**
     * 获取kafka builder工具
     * @return 构造器
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * 生产者构建
     */
    public static class ProducerBuilder implements BaseBuilder<ProducerBean> {

        private ProducerBean producer;

        ProducerBuilder() {
            producer = new ProducerBean();
        }

        /**
         * 是否开启当前的生产者 默认不开启
         * @param enable 开启
         * @return
         */
        public ProducerBuilder enable(boolean enable){
            this.producer.setEnable(enable);
            return this;
        }

        /**
         * 设置生产者服务、不设置根据通用的进行配置（通用默认是本地127.0.0.1:9200)
         * @param servers 服务ip端口
         * @return
         */
        public ProducerBuilder servers(String servers) {
            this.producer.setServers(servers);
            return this;
        }

        /**
         * 设置生产序列化key的类、默认apache string的序列化类
         * @param keySerializer 序列化
         * @return
         */
        public ProducerBuilder keySerializer(String keySerializer) {
            this.producer.setKeySerializer(keySerializer);
            return this;
        }

        /**
         * 设置生产序列化value的类、默认apache string的序列化类
         * @param valueSerializer 序列化
         * @return
         */
        public ProducerBuilder valueSerializer(String valueSerializer) {
            this.producer.setValueSerializer(valueSerializer);
            return this;
        }

        /**
         * 设置应答、默认all
         * @param acks 应答
         * @return
         */
        public ProducerBuilder acks(String acks) {
            this.producer.setAcks(acks);
            return this;
        }

        public ProducerBuilder retries(String retries) {
            this.producer.setRetries(retries);
            return this;
        }

        public ProducerBuilder batchSize(Integer batchSize) {
            this.producer.setBatchSize(batchSize);
            return this;
        }

        public ProducerBuilder lingerMs(Integer lingerMs) {
            this.producer.setLingerMs(lingerMs);
            return this;
        }

        public ProducerBuilder bufferMemory(Long bufferMemory) {
            this.producer.setBufferMemory(bufferMemory);
            return this;
        }

        public ProducerBuilder partitionerClass(String partitionerClass) {
            this.producer.setPartitionerClass(partitionerClass);
            return this;
        }

        @Override
        public ProducerBean builder() {
            return this.producer;
        }
    }

    /**
     * 消费者构建
     */
    public static class ConsumerBuilder implements BaseBuilder<ConsumerBean> {

        private ConsumerBean consumer;

        ConsumerBuilder() {
            this.consumer = new ConsumerBean();
        }

        public ConsumerBuilder enable(boolean enable){
            this.consumer.setEnable(enable);
            return this;
        }
        public ConsumerBuilder servers(String servers) {
            this.consumer.setServers(servers);
            return this;
        }

        public ConsumerBuilder keyDeserializer(String keyDeserializer) {
            this.consumer.setKeyDeserializer(keyDeserializer);
            return this;
        }

        public ConsumerBuilder valueDeserializer(String valueDeserializer) {
            this.consumer.setValueDeserializer(valueDeserializer);
            return this;
        }

        public ConsumerBuilder invokeBeanName(String invokeBeanName) {
            this.consumer.setInvokeBeanName(invokeBeanName);
            return this;
        }

        public ConsumerBuilder invokeMethodName(String invokeMethodName) {
            this.consumer.setInvokeMethodName(invokeMethodName);
            return this;
        }

        public ConsumerBuilder invokeCallback(String invokeCallback) {
            this.consumer.setInvokeCallback(invokeCallback);
            return this;
        }

        public ConsumerBuilder topics(String topics) {
            this.consumer.setTopics(topics);
            return this;
        }

        public ConsumerBuilder groupId(String groupId) {
            this.consumer.setGroupId(groupId);
            return this;
        }

        public ConsumerBuilder pull(Integer pull) {
            this.consumer.setPull(pull);
            return this;
        }

        public ConsumerBuilder threadPartitionNum(Integer threadPartitionNum) {
            this.consumer.setThreadPartitionNum(threadPartitionNum);
            return this;
        }

        public ConsumerBuilder enableAutoCommit(Boolean enableAutoCommit) {
            this.consumer.setEnableAutoCommit(enableAutoCommit);
            return this;
        }

        public ConsumerBuilder autoCommitIntervalMs(Integer autoCommitIntervalMs) {
            this.consumer.setAutoCommitIntervalMs(autoCommitIntervalMs);
            return this;
        }

        public ConsumerBuilder sessionTimeOutMs(Integer sessionTimeOutMs) {
            this.consumer.setSessionTimeOutMs(sessionTimeOutMs);
            return this;
        }

        public ConsumerBuilder autoOffsetReset(String autoOffsetReset) {
            this.consumer.setAutoOffsetReset(autoOffsetReset);
            return this;
        }

        @Override
        public ConsumerBean builder() {
            return this.consumer;
        }
    }

    /**
     * 全局构建
     */
    public static class Builder implements BaseBuilder<KafkaFramework> {

        /**
         * 对应的通用服务
         */
        private KafkaInfo info;

        public Builder() {
            info = new KafkaInfo();
        }

        public Builder servers(String servers) {
            info.setServers(servers);
            return this;
        }

        public Builder keySerializer(String keySerializer) {
            info.setKeySerializer(keySerializer);
            return this;
        }

        public Builder valueSerializer(String valueSerializer) {
            info.setValueSerializer(valueSerializer);
            return this;
        }

        public Builder keyDeserializer(String keyDeserializer) {
            info.setKeyDeserializer(keyDeserializer);
            return this;
        }

        public Builder valueDeserializer(String valueDeserializer) {
            info.setValueDeserializer(valueDeserializer);
            return this;
        }

        public Builder enableProducer(Boolean enableProducer) {
            info.setEnableProducer(enableProducer);
            return this;
        }

        public Builder enableConsumer(Boolean enableConsumer) {
            info.setEnableConsumer(enableConsumer);
            return this;
        }

        public Builder bindProducer(String key, ProducerBean value) {
            info.getProducerMap().put(key, value);
            return this;
        }

        public Builder bindConsumer(String key, ConsumerBean value) {
            info.getConsumerMap().put(key, value);
            return this;
        }

        public ProducerBuilder createProducerBuilder() {
            return new ProducerBuilder();
        }

        public ConsumerBuilder createConsumerBuilder() {
            return new ConsumerBuilder();
        }

        @Override
        public KafkaFramework builder() {
            return new KafkaFramework(info);
        }
    }
}
