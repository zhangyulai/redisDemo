package com.zhangyl.redis.controller;

import com.zhangyl.redis.enums.DelayQueueEnum;
import com.zhangyl.redis.utils.RedissonDelayQueueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列测试
 *
 * @author zyl
 * @email zyl@zhangyl.com
 * @date 2023-04-19 15:30
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class RedissonDelayQueueController {

    private final RedissonDelayQueueUtil redissonDelayQueueUtil;

    /**
     * 添加订单支付超时测试数据
     *
     * @param count 订单数量
     */
    @PostMapping("/addQueue1")
    public void addQueue1(Integer count) {
        for (int i = 0; i < count; i++) {
            Integer random = new Random().nextInt(100) + 1;
            Map<String, String> map = new HashMap<>();
            map.put("orderId", String.valueOf(i));
            map.put("remark", "订单支付超时，自动取消订单");
            map.put("random", String.valueOf(random));
            map.put("timestamp", String.valueOf(System.currentTimeMillis()));
            redissonDelayQueueUtil.add(DelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode(), map, random,
                    TimeUnit.SECONDS);
        }
    }

    @PostMapping("/delete")
    public void delete(Integer id, Integer random, Long timestamp) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", String.valueOf(id));
        map.put("remark", "订单支付超时，自动取消订单");
        map.put("random", String.valueOf(random));
        map.put("timestamp", String.valueOf(timestamp));
        redissonDelayQueueUtil.remove(DelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode(), map);
    }

    /**
     * 添加订单超时未评价测试数据
     *
     * @param count 订单数量
     */
    @PostMapping("/addQueue2")
    public void addQueue2(Integer count) {
        for (int i = 0; i < count; i++) {
            Integer random = new Random().nextInt(100) + 1;
            Map<String, String> map = new HashMap<>();
            map.put("orderId", String.valueOf(i));
            map.put("remark", "订单超时未评价，系统默认好评");
            map.put("random", String.valueOf(random));
            map.put("timestamp", String.valueOf(System.currentTimeMillis()));
            redissonDelayQueueUtil.add(DelayQueueEnum.ORDER_EVALUATED_TIMEOUT.getCode(), map,
                    random, TimeUnit.SECONDS
            );
        }
    }
}