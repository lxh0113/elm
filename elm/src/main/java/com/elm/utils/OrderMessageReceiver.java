package com.elm.utils;

import com.alibaba.fastjson.JSON;
import com.elm.dao.OrdersDao;
import com.elm.domain.Orders;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

//@Component
//public class OrderMessageReceiver{
//
//    @Autowired
//    private OrdersDao ordersDao;
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @RabbitListener(queues = "order_queue", concurrency = "5") // 设置并发消费者数量
//    @Scheduled(fixedDelay = 1000 * 60 * 15) // 15分钟执行一次
//    public void receiveOrderMessage() {
//        try {
//            // 这里可以根据具体情况从消息队列中获取订单消息
//            List<String> orderMessages = getOrdersFromQueue();
//
//            for (String orderJson : orderMessages) {
//                // 将收到的 JSON 字符串转换为 Orders 对象
//                Orders orders = JSON.parseObject(orderJson, Orders.class);
//
//                // 检查订单支付状态，如果未支付且超过15分钟，则设置为未支付
//                if (orders.getStatus() == 0 && isPaymentOverdue(orders.getTime())) {
//                    orders.setStatus(2); // 2 表示订单未支付
//                    ordersDao.updateById(orders);
//                    System.out.println("Order payment overdue. Set status to unpaid: " + orderJson);
//                } else {
//                    // 执行其他订单处理逻辑，例如更新订单状态
//                    // 此处仅作为示例，你需要根据实际需求进行处理
//                    orders.setStatus(1); // 1 表示订单已处理
//                    ordersDao.updateById(orders);
//                    System.out.println("Received order from the queue: " + orderJson);
//                }
//            }
//        } catch (Exception e) {
//            // 处理异常
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isPaymentOverdue(Date createTime) {
//        // 检查订单创建时间与当前时间的差是否超过15分钟
//        long currentTimeMillis = System.currentTimeMillis();
//        long createTimeMillis = createTime.getTime();
//        long fifteenMinutesInMillis = 15 * 60 * 1000; // 15分钟的毫秒数
//
//        return (currentTimeMillis - createTimeMillis) > fifteenMinutesInMillis;
//    }
//
//    private List<String> getOrdersFromQueue() {
//        // 这里可以根据具体情况从消息队列中获取订单消息
//        // 返回一个包含订单消息的列表
////        return rabbitTemplate.receive("order_queue");
//        return null;
//    }
//
//}

