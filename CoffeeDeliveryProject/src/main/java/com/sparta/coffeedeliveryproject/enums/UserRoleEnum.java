package com.sparta.coffeedeliveryproject.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    USER(Authority.USER), // 일반 유저
    ADMIN(Authority.ADMIN); // 관리자

    private final String role;

    UserRoleEnum(String role) {
        this.role = role;
    }

    private class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

}