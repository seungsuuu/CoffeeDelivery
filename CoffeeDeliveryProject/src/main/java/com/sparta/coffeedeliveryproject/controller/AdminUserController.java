package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.dto.UserEditRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserResponseDto;
import com.sparta.coffeedeliveryproject.exceptions.PasswordMismatchException;
import com.sparta.coffeedeliveryproject.exceptions.RecentlyUsedPasswordException;
import com.sparta.coffeedeliveryproject.exceptions.UserNotFoundException;
import com.sparta.coffeedeliveryproject.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final AdminUserService adminUserService;

    // 전체 유저 조회
    @GetMapping("/users")
    public List<UserResponseDto> gellAllUsers() {
        return adminUserService.getAllUsers();
    }

    // 특정 유저 수정
    @PutMapping("/users/{userId}")
    public UserResponseDto editUser(@PathVariable Long userId, @RequestBody UserEditRequestDto userEditRequestDto) {
        return adminUserService.editUser(userId, userEditRequestDto);
    }

    // 특정 유저 삭제
    @DeleteMapping("/users/{userId}")
    public MessageResponseDto deleteUser(@PathVariable Long userId) {
        return adminUserService.deleteUser(userId);
    }

    // 특정 유저 admin으로 권한 부여
    @PutMapping("/users/authority/{userId}")
    public UserResponseDto changeUserRoleToAdmin(@PathVariable long userId) {
        return adminUserService.changeUserRoleToAdmin(userId);
    }

    // 특정 유저 block
    @PutMapping("/users/block/{userId}")
    public UserResponseDto userBlock(@PathVariable long userId) {
        return adminUserService.userBlock(userId);
    }

    @ExceptionHandler // 에러 핸들링
    private ResponseEntity<String> handleException(RecentlyUsedPasswordException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler // 에러 핸들링
    private ResponseEntity<String> handleException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler // 에러 핸들링
    private ResponseEntity<String> handleException(PasswordMismatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
