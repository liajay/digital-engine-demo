package com.liajay.demo.user.service;

import com.liajay.demo.common.exception.ErrorCode;
import com.liajay.demo.common.exception.SystemException;
import com.liajay.demo.common.model.dto.UserOperationLog;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LogProducerService {
    private static final Logger log = LoggerFactory.getLogger(LogProducerService.class);
    private final String topic;
    private final RocketMQTemplate rocketMQTemplate;

    public LogProducerService(RocketMQTemplate rocketMQTemplate,  @Value("${rocketmq.logging.topic}") String topic) {
        this.rocketMQTemplate = rocketMQTemplate;
        this.topic = topic;
    }

    //TODO: 后续再加点补偿逻辑
    public void sendOperationLog(UserOperationLog logDTO) {
        rocketMQTemplate.asyncSend(topic, logDTO, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("send mq succeed");
            }

            @Override
            public void onException(Throwable e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
