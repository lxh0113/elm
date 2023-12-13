package com.elm.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Delayed;

@Slf4j
@Component
public class MqListener {

    @RabbitListener(queues = "test.queue")
    public void ListenObject(String msg)
    {
        System.out.println("收到了"+msg);
    }



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "delay.test.queue", durable = "true"),
            exchange = @Exchange(value = "delay.test",delayed = "true",type = "x-delayed-message", arguments = @Argument(name = "x-delayed-type", value = "direct")),
            key = "lzy"
    ))
    public void listenTestDelay(Integer msg) {

//        System.out.println("-------------------------------");
//        System.out.println(msg);
//        System.out.println("消息接收时间 "+new Date(System.currentTimeMillis()));
        log.info("{}",new Date(System.currentTimeMillis()));
        log.info("收到{}的消息",msg);
    }


}
