package com.elm.Amqp;

import com.elm.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AmqpSend {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    public void sendMessage(Integer orderId) {
        rabbitTemplate.convertAndSend("delay.test", "lzy", orderId,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置消息持久化
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                message.getMessageProperties().setHeader("x-delay",1000*10);//设置延时时间 15分钟
                return message;
            }
        });


        log.info("{}",new Date(System.currentTimeMillis()));
        log.info("发送消息成功");
    }
}
