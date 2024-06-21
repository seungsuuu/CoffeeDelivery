package com.sparta.coffeedeliveryproject.controller;

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

}
