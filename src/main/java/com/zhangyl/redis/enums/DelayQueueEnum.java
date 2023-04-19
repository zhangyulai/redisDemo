package com.zhangyl.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 延迟队列枚举
 *
 * @author zyl
 * @email zyl@zhangyl.com
 * @date 2023-04-19 15:30
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum DelayQueueEnum {

    ORDER_PAYMENT_TIMEOUT("ORDER_PAYMENT_TIMEOUT", "订单支付超时，自动取消订单", "orderPaymentTimeout"),
    ORDER_EVALUATED_TIMEOUT("ORDER_EVALUATED_TIMEOUT", "订单评价超时，自动好评", "orderEvaluatedTimeout");

    /**
     * 延迟队列Key
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 延迟队列具体业务实现的Bean
     * 可通过 Spring 的上下文获取
     */
    private String beanId;

}
