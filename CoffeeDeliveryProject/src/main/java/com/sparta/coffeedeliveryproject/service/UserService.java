package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.SignupRequestDto;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    // WebSecurityConfig 클래스에 구현 후 사용 예정
//    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String signup(SignupRequestDto signupRequestDto) {

        String nickName = signupRequestDto.getNickName();
        // WebSecurityConfig 클래스 구현 후 비밀번호 인코딩 예정
        String password = signupRequestDto.getPassword();
        signupRequestDto.setPassword(password);

        // 회원 중복 확인
        Optional<User> checkNickName = userRepository.findByNickName(nickName);
        if (checkNickName.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(signupRequestDto);

        userRepository.save(user);

        return "회원가입 성공";
    }

}