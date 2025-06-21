package com.liajay.demo.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record UserInfo(long id, String username, String email, String phone) {

}
