package com.liajay.demo.user.config;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfig {

//    @Bean
//    public RocketMQTemplate rocketMQTemplate(RocketMQMessageConverter messageConverter) {
//        RocketMQTemplate template = new RocketMQTemplate();
//        template.setMessageConverter(messageConverter.getMessageConverter());
//        return template;
//    }
}