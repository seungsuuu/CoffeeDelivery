package com.sparta.coffeedeliveryproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserStatus> userStatus = new ArrayList<>();

    // 컬렉션 객체임을 알려줌
    @ElementCollection
    // pastPasswords의 데이터를 past_passwords 테이블에 저장후에 user_id의 컬럼과 join
    @CollectionTable(name = "past_passwords", joinColumns = @JoinColumn(name = "user_id"))
    // pastPasswords 테이블의 컬렉션 값이 past_password로 저장
    @Column(name = "past_password")
    private Queue<String> pastPasswords = new LinkedList<>();

    @Column(nullable = false)
    private String refreshToken;

    public User(String userName, String password, String nickName, List<UserRole> userRoles, List<UserStatus> userStatus) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.userRoles = userRoles;
        this.userStatus = userStatus;
    }
}
