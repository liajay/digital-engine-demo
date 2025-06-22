package com.liajay.demo.logging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(value = "com.liajay.demo")
public class LogServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(LogServiceApplication.class);
    }
}


