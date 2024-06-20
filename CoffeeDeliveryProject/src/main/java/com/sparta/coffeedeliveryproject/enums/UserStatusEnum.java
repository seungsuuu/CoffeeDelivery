package com.sparta.coffeedeliveryproject.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {

    ACTIVE(Authority.ACTIVE), // 정상
    BLOCK(Authority.BLOCK); // 차단

    private final String status;

    UserStatusEnum(String status) {
        this.status = status;
    }

    private class Authority {
        public static final String ACTIVE = "ACTIVE";
        public static final String BLOCK = "BLOCK";
    }

}