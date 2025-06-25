package com.liajay.demo.user.model.entity;

import com.liajay.demo.common.model.dto.UserInfo;
import jakarta.persistence.*;
import org.apache.shardingsphere.infra.algorithm.keygen.snowflake.SnowflakeKeyGenerateAlgorithm;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;

@Entity
@Table(name = "users")
public class User {
    private static final Random generator = new Random();

    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column
    private LocalDateTime gmt_create;

    public User() {
    }


    //TODO:修改id生成
    @PrePersist
    protected void generateId(){
        if (id == null || id == 0){
            id = generator.nextLong(1,1000000L);
        }

        if (this.gmt_create == null){
            this.gmt_create = LocalDateTime.now();
        }

    }
    public UserInfo toUserInfo(){
        return new UserInfo(
                getId() == null ? 0 : getId(),
                getUsername(),
                getEmail(),
                getPhone()
        );
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(LocalDateTime gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
