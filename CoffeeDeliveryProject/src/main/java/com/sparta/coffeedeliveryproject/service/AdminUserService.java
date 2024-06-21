package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.UserResponseDto;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        // 모든 유저를 UserResponseDto로 매핑하여 list로 들어 반환
        return users.stream()
                .map(user -> new UserResponseDto(user))
                .collect(Collectors.toList());
    }

}
