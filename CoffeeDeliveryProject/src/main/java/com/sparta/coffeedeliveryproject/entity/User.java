package com.sparta.coffeedeliveryproject.entity;

import com.sparta.coffeedeliveryproject.enums.UserRoleEnum;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;

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

    // 컬렉션 객체임을 알려줌
    @ElementCollection
    // pastPasswords의 데이터를 past_passwords 테이블에 저장후에 user_id의 컬럼과 join
    @CollectionTable(name = "past_passwords", joinColumns = @JoinColumn(name = "user_id"))
    // pastPasswords 테이블의 컬렉션 값이 past_password로 저장
    @Column(name = "past_password")
    private Queue<String> pastPasswords = new LinkedList<>();

    @Column(nullable = false)
    private String refreshToken;
}
