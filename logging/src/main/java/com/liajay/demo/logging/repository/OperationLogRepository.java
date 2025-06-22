package com.liajay.demo.logging.repository;

import com.liajay.demo.logging.model.entity.UserOperationLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogRepository extends JpaRepository<UserOperationLogEntity, Long> {
}
