package com.zhangyl.redis.init;

import com.zhangyl.redis.enums.DelayQueueEnum;
import com.zhangyl.redis.handle.DelayQueueMessageHandle;
import com.zhangyl.redis.utils.RedissonDelayQueueUtil;
import com.zhangyl.redis.utils.SpringContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 启动延迟队列
 *
 * @author zyl
 * @email zyl@zhangyl.com
 * @date 2023-04-19 15:30
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Component
public class RedissonDelayQueueRunner implements CommandLineRunner {

    // 是否销毁标记 volatile 保证可见性
    private volatile boolean destroy = false;
    private final RedissonDelayQueueUtil redissonDelayQueueUtil;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void run(String... args) {
        DelayQueueEnum[] queueEnums = DelayQueueEnum.values();
        for (DelayQueueEnum queueEnum : queueEnums) {
            new Thread(() -> {
                while (!destroy) {
                    try {
                        Object value = redissonDelayQueueUtil.get(queueEnum.getCode());
                        if (!destroy && value != null) {
                            DelayQueueMessageHandle delayQueueMessageHandle =
                                    SpringContextUtils.getBean(queueEnum.getBeanId());
                            threadPoolTaskExecutor.execute(() -> delayQueueMessageHandle.handle(value));
                        }
                    } catch (Exception e) {
                        log.error("Redisson延迟队列异常中断：{}", e.getMessage());
                    }
                }
            }).start();
        }
        log.info("【Redisson延迟队列启动成功】");
    }

    @PreDestroy
    public void destroy() {
        log.debug("应用已关闭...");
        this.destroy = true;
    }

}