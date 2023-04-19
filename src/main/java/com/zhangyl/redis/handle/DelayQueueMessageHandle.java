package com.zhangyl.redis.handle;

/**
 * 延迟队列消息处理接口
 *
 * @author zyl
 * @email zyl@zhangyl.com
 * @date 2023-04-19 15:30
 */
public interface DelayQueueMessageHandle<T> {

    void handle(T t);

}