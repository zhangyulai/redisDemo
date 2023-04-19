package com.zhangyl.redis.handle.impl;

import com.zhangyl.redis.handle.DelayQueueMessageHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单评价超时处理
 *
 * @author zyl
 * @email zyl@zhangyl.com
 * @date 2023-04-19 15:30
 */
@Slf4j
@Service
public class OrderEvaluatedTimeout implements DelayQueueMessageHandle<Map> {
    @Override
    public void handle(Map map) {
        // 自动好评处理……
        Long now = System.currentTimeMillis();
        Long timestamp = Long.valueOf(String.valueOf(map.get("timestamp")));
        Long delayTime = now - timestamp;
        Long random = Long.valueOf(String.valueOf(map.get("random")));
        Long diffTime = delayTime - random * 1000;
        log.info("订单评价超时处理：orderId：{}, 预计延迟时间：{} 秒，实际延迟时间：{} 毫秒，相差：{} 毫秒",
                map.get("orderId"), random, delayTime, diffTime);
    }

}
