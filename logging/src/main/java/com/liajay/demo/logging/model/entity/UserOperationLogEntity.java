package com.liajay.demo.logging.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_operation")
public record UserOperationLogEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long id,

        @Column(name = "user_id", nullable = false)
        long userId,

        @Column(name = "action", length = 50)
        String action,

        @Column(name = "ip", length = 15)
        String ip,

        @Column(name = "detail")
        String detail
) {
        public UserOperationLogEntity(long userId, String ip, String detail, String action) {
                this(0, userId, action, ip, detail);
        }
}
