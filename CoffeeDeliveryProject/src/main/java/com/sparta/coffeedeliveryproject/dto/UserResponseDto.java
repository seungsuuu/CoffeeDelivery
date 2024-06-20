package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.entity.UserStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponseDto {

    private Long userId;

    private String userName;

    private String password;

    private String nickName;

    private List<UserRole> userRole;

    private List<UserStatus> userStatus;

    private String refreshToken;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.nickName = user.getNickName();
        this.userRole = user.getUserRoles();
        this.userStatus = user.getUserStatus();
        this.refreshToken = user.getRefreshToken();
    }

}
