package com.cn.base.config;



import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {
    private static final String QUEUE = "queue";
    
    //创建队列
    //true:表示持久化
    //队列在默认情况下放到内存，rabbitmq重启后就丢失了，如果希望重启后，队列
    //数据还能使用，就需要持久化
    @Bean
    public Queue queue1(){
        return new Queue(QUEUE,true);
    }
}