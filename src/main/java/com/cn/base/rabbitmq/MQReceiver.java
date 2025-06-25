package com.cn.base.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {
    
    //方法：接收消息
    @RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接收到的消息-->" + msg);
    }
 }  