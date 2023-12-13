package com.elm.controller;

import com.elm.config.RabbitConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest
@Component
public class MqDeadTest {

    @Autowired
    RabbitmqPublish rabbitmqPublish;

   @Test
    void send() {
        //发送多个延时消息
        rabbitmqPublish.sendTimeoutMsg("hello1" , "routingKey1" ,40);
        rabbitmqPublish.sendTimeoutMsg("hello2" , "routingKey2" ,20);
        rabbitmqPublish.sendTimeoutMsg("hello3" , "routingKey1" ,60);

        //发送普通消息
        rabbitmqPublish.sendMsg(RabbitConfig.Order_Pay_Queue_Name , "weixin");
        rabbitmqPublish.sendMsg(RabbitConfig.Order_Pay_Queue_Name , "alipay");

//        return "success";
    }

}
