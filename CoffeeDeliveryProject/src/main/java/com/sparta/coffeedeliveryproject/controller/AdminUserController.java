package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.UserResponseDto;
import com.sparta.coffeedeliveryproject.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
