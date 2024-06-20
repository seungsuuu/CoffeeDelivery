package com.sparta.coffeedeliveryproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserRoleMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleMatchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // 사용자와의 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role")
    private UserRole userRole;  // 사용자의 역할과의 관계

}
