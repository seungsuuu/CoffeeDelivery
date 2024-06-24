package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.dto.UserEditRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final AdminUserService adminUserService;

    // 전체 유저 조회
    @GetMapping("/users")
    public List<UserResponseDto> gellAllUsers(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminUserService.getAllUsers(userDetails.getUser().getUserId());
    }

    // 특정 유저 수정
    @PutMapping("/users/{userId}")
    public UserResponseDto editUser(@PathVariable Long userId, @RequestBody UserEditRequestDto userEditRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminUserService.editUser(userId, userEditRequestDto, userDetails.getUser().getUserId());
    }

    // 특정 유저 삭제
    @DeleteMapping("/users/{userId}")
    public MessageResponseDto deleteUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminUserService.deleteUser(userId, userDetails.getUser().getUserId());
    }

    // 특정 유저 admin으로 권한 부여
    @PutMapping("/users/authority/{userId}")
    public UserResponseDto changeUserRoleToAdmin(@PathVariable long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminUserService.changeUserRoleToAdmin(userId, userDetails.getUser().getUserId());
    }

    // 특정 유저 block
    @PutMapping("/users/block/{userId}")
    public UserResponseDto userBlock(@PathVariable long userId, UserDetailsImpl userDetails) {
        return adminUserService.userBlock(userId, userDetails.getUser().getUserId());
    }

}
