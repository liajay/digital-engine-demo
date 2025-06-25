package com.liajay.demo.user;


import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(value = "com.liajay.demo")
@EnableFeignClients("com.liajay.demo.common.feign")
@EnableJpaRepositories(basePackages = "com.liajay.demo.user.repository")
public class UserServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(UserServiceApplication.class);
    }
}
