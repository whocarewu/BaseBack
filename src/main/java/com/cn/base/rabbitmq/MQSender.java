package com.cn.base.rabbitmq;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MQSender {
    //装配RabbitTemplate ->操作RabbitMQ
    @Resource
    private RabbitTemplate rabbitTemplate;

    //方法:发送消息
    public void send(Object msg){
        log.info("发送消息-->" + msg);
        //指定你队列的名字
        rabbitTemplate.convertAndSend("queue",msg);
    }
}