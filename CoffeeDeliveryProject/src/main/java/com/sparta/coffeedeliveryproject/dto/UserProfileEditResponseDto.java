package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserProfileEditResponseDto {

    private String nickName;

    private String password;

    public UserProfileEditResponseDto(User user) {
        this.nickName = user.getNickName();
        this.password = user.getPassword();
    }

}
