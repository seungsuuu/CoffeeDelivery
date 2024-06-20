package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.enums.UserRoleEnum;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long userId;

    private String userName;

    private String password;

    private String nickName;

    private UserRoleEnum userRole;

    private UserStatusEnum userStatus;

    private String refreshToken;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.nickName = user.getNickName();
        this.userRole = user.getUserRole();
        this.userStatus = user.getUserStatus();
        this.refreshToken = user.getRefreshToken();
    }

}
