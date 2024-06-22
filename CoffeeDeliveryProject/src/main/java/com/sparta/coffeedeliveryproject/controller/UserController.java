package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.dto.SignupRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserProfileEditRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserProfileEditResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto) {

        userService.signup(signupRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PutMapping("/profile")
    public UserProfileEditResponseDto editProfile(@RequestBody UserProfileEditRequestDto userProfileEditRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.editProfile(userProfileEditRequestDto, userDetails.getUser().getUserId());
    }

    @PostMapping("/loggout")
    public MessageResponseDto logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.logout(userDetails.getUser().getUserId());
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}