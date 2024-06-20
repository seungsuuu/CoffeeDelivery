package com.sparta.coffeedeliveryproject.entity;

import com.sparta.coffeedeliveryproject.enums.UserRoleEnum;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatusEnum userStatus;

    @Column(nullable = false)
    private String refreshToken;
}
