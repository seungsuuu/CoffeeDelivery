package com.sparta.coffeedeliveryproject.dto;

import lombok.Getter;

@Getter
public class UserProfileEditRequestDto {

    private String newNickName;

    private String password;

    private String newPassword;

}
