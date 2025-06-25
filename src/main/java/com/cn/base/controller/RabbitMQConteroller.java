package com.cn.base.controller;

import com.cn.base.rabbitmq.MQSender;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rabbit")
@RestController
public class RabbitMQConteroller {

    @Resource
    private MQSender mqSender;

    @GetMapping("/mq")
    //方法：调用消息生产者，发送消息
    public void mq(){
        mqSender.send("hello,消费者~");
    }
}