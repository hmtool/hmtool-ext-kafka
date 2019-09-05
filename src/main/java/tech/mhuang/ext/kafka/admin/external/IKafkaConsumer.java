package tech.mhuang.ext.kafka.admin.external;

import tech.mhuang.core.reflect.BaseReflectInvoke;

/**
 * 消费者接口--可自行实现
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface IKafkaConsumer<T> extends Runnable {

    /**
     * 回调
     * @param invoke 回调处理的方式
     * @return kafka消费者
     */
    IKafkaConsumer invoke(BaseReflectInvoke invoke);

    /**
     * 执行
     * @param t 执行的参数
     */
    public void execute(T t);
}
