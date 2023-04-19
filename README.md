# Redis、Redisson的一些特性示例

## 更新日志
- v1.0.0 (20230419)
  1. `F` Redisson延迟队列示例 [官方文档](https://github.com/redisson/redisson/wiki/7.-distributed-collections/#715-delayed-queue)
        支持新增、删除延迟队列对象；重启服务后可正常消费过期消息；每一个队列单独起一个线程获取过期消息；消费过期消息使用线程池异步处理。