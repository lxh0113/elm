package com.elm.Amqp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.dao.OrdersDao;
import com.elm.domain.Orders;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
public class AmqpReceive {

    @Autowired
    private OrdersDao ordersDao;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "delay.test.queue", durable = "true"),
            exchange = @Exchange(value = "delay.test",delayed = "true",type = "x-delayed-message", arguments = @Argument(name = "x-delayed-type", value = "direct")),
            key = "lzy"
    ))
    public void listenTestDelay(Integer orderId) {

        System.out.println("收到消息"+orderId);

        //对订单进行处理
        QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",orderId)
                        .eq("status",0);

        Orders orders = ordersDao.selectOne(queryWrapper);

        if(orders!=null)
        {
            orders.setStatus(2);
            ordersDao.updateById(orders);
        }
        else System.out.println("没找到"+orderId+"的消息");

        log.info("{}",new Date(System.currentTimeMillis()));
        log.info("收到{}的消息",orderId);
    }
}
