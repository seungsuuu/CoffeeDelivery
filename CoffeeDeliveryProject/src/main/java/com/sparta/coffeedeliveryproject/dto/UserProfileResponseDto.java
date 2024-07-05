package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserProfileResponseDto {

    private Long userId;

    private String userName;

    private String nickName;

    private Long myLikesCafeCount;

    private Long myLikesReviewCount;

    private UserStatusEnum userStatus;

    private Set<UserRole> userRoles;

    public UserProfileResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.myLikesCafeCount = user.getMyLikesCafeCount();
        this.myLikesReviewCount = user.getMyLikesReviewCount();
        this.userStatus = user.getUserStatus();
        this.userRoles = user.getUserRoles();
    }

}
