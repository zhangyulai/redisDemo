package com.zhangyl.redis.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redisson延迟队列工具类
 *
 * @author zyl
 * @email zyl@zhangyl.com
 * @date 2023-04-19 15:30
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Component
public class RedissonDelayQueueUtil {

    private final RedissonClient redissonClient;

    /**
     * 添加元素到延迟队列
     *
     * @param queueCode 队列键
     * @param element   元素
     * @param delay     延迟时间
     * @param timeUnit  时间单位
     */
    public <T> void add(String queueCode, T element, long delay, TimeUnit timeUnit) {
        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        delayedQueue.offer(element, delay, timeUnit);
        log.info("【添加元素到延迟队列成功】队列：{}，元素：{}，延迟时间：{}", queueCode, element, timeUnit.toSeconds(delay) + "秒");
    }

    /**
     * 获取延迟队列的第一个元素
     *
     * @param queueCode 队列键
     * @return 指定队列的第一个元素
     */
    public <T> T get(String queueCode) {
        RBlockingDeque<T> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        redissonClient.getDelayedQueue(blockingDeque);
        return blockingDeque.poll();
    }

    /**
     * 删除指定队列中的元素
     *
     * @param queueCode 指定队列键
     * @param element   指定删除的元素(同队列需保证唯一性)
     */
    public void remove(String queueCode, Object element) {
        if (queueCode == null || queueCode.length() == 0 || Objects.isNull(element)) {
            return;
        }
        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        if (delayedQueue.remove(element)) {
            log.info("【从延迟队列删除元素成功】队列: {}，元素：{}", queueCode, element);
        }
    }

}