package com.sparta.coffeedeliveryproject.entity;

import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatusEnum userStatus;

    // 사용자의 역할을 나타내는 UserRole 엔티티와의 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", referencedColumnName = "role")
    private UserRole userRole;

    // 컬렉션 객체임을 알려줌
    @ElementCollection
    // pastPasswords의 데이터를 past_passwords 테이블에 저장후에 user_id의 컬럼과 join
    @CollectionTable(name = "past_passwords", joinColumns = @JoinColumn(name = "user_id"))
    // pastPasswords 테이블의 컬렉션 값이 past_password로 저장
    @Column(name = "past_password")
    private List<String> pastPasswords = new LinkedList<>();

    @Column(nullable = false)
    private String refreshToken;

    public User(String userName, String password, String nickName, UserRole userRole, UserStatusEnum userStatusEnum, String refreshToken) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.userRole = userRole;
        this.userStatus = userStatusEnum;
        this.refreshToken = refreshToken;
    }

}
