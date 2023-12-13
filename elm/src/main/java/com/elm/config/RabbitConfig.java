package com.elm.config;


import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitConfig {

    // 支付超时延时交换机
    public static final String Delay_Exchange_Name = "delay.exchange";

    //exchange name
    public static final String Default_Exchange_Name = "test.exchange";

    //微信支付后，发送支付队列
    public static final String Order_Pay_Queue_Name = "order_pay";

    //超时订单关闭队列
    public static final String Timeout_Trade_Queue_Name = "close_trade";

    @Bean
    public Queue orderPayQueue(){
        return new Queue(RabbitConfig.Order_Pay_Queue_Name , true);
    }

    @Bean
    public Queue delayPayQueue() {
        return new Queue(RabbitConfig.Timeout_Trade_Queue_Name, true);
    }


    // 定义广播模式的延时交换机 无需绑定路由
    @Bean
    FanoutExchange delayExchange(){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        FanoutExchange topicExchange = new FanoutExchange(RabbitConfig.Delay_Exchange_Name, true, false, args);
        topicExchange.setDelayed(true);
        return topicExchange;
    }
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(RabbitConfig.Default_Exchange_Name, true, false);
    }


    // 绑定延时队列与交换机
    @Bean
    public Binding delayPayBind() {
        return BindingBuilder.bind(delayPayQueue()).to(delayExchange());
    }

    // 绑定普通消息队列
    @Bean
    public Binding orderPayBind(){
        return BindingBuilder.bind(orderPayQueue()).to(defaultExchange()).with(RabbitConfig.Order_Pay_Queue_Name);
    }

    // 定义消息转换器
    @Bean
    Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 定义消息模板用于发布消息，并且设置其消息转换器
    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    RabbitAdmin rabbitAdmin(final ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}

