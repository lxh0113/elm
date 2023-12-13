package com.elm.controller;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class MqTest {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Test
    void mqTest()
    {
        rabbitTemplate.convertAndSend("test.queue","你好");
    }

    @Test
    void testDelay() {
        rabbitTemplate.convertAndSend("delay.test", "lzy", 123,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置消息持久化
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                message.getMessageProperties().setHeader("x-delay",1000*10);//设置延时时间
                return message;
            }
        });
        log.info("{}",new Date(System.currentTimeMillis()));
        log.info("发送消息成功");
    }
}
