package com.liajay.demo.logging.consumer;

import com.liajay.demo.common.model.dto.UserOperationLog;
import com.liajay.demo.logging.model.entity.UserOperationLogEntity;
import com.liajay.demo.logging.repository.OperationLogRepository;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RocketMQMessageListener(
        nameServer = "${rocketmq.nameserver}",
        topic = "${rocketmq.logging.topic}",
        consumerGroup = "log-consumer-group"
)
public class LogMessageConsumer implements RocketMQListener<UserOperationLog> {

    private OperationLogRepository operationLogRepository;

    public LogMessageConsumer(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    @Override
    @Transactional
    public void onMessage(UserOperationLog userOperationLog) {
        UserOperationLogEntity entity = new UserOperationLogEntity(
                userOperationLog.getUserId(),
                userOperationLog.getIp(),
                userOperationLog.getDetail(),
                userOperationLog.getAction()
        );

        operationLogRepository.save(entity);
    }
}
