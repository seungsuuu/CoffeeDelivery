package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.dto.SignupRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserProfileEditRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserProfileEditResponseDto;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.exceptions.PasswordMismatchException;
import com.sparta.coffeedeliveryproject.exceptions.RecentlyUsedPasswordException;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public MessageResponseDto signup(SignupRequestDto signupRequestDto) {

        String nickName = signupRequestDto.getNickName();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        signupRequestDto.setPassword(password);

        // 회원 중복 확인
        Optional<User> checkNickName = userRepository.findByNickName(nickName);
        if (checkNickName.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(signupRequestDto);

        userRepository.save(user);

        return new MessageResponseDto("회원가입이 완료되었습니다.");
    }

    public UserProfileEditResponseDto editProfile(UserProfileEditRequestDto userProfileEditRequestDto, Long userId) {
        User user = findUserById(userId);

        if (userProfileEditRequestDto.getNewNickName() != null) {
            user.editUserNickName(userProfileEditRequestDto.getNewNickName());
        }

        // 현재 비밀번호 확인
        if (userProfileEditRequestDto.getPassword() != null) {
            // TODO: 나중에 password 암호화되면 encoder 추가 필요
            // 일치하지 않으면
            if (!userProfileEditRequestDto.getPassword().matches(user.getPassword())) {
                throw new PasswordMismatchException("현재 비밀번호가 일치하지 않습니다.");
            }

            // 이전 비밀번호에 새로운 비밀번호가 있으면
            if (user.getPastPasswords().contains(userProfileEditRequestDto.getNewPassword())) {
                throw new RecentlyUsedPasswordException("최근 설정한 4개의 비밀번호로는 비밀번호를 변경하실 수 없습니다.");
            }

            // 새로운 비밀번호 설정
            user.editUserPassword(userProfileEditRequestDto.getNewPassword());

            // 과거 비밀번호 저장하는 리스트의 갯수가 4개인지 확인
            if (user.getPastPasswords().stream().count() == 4) {

                // 가장 먼저 저장된 password 삭제
                user.getPastPasswords().remove(0);

                // 새로운 비밀번호 저장
                user.getPastPasswords().add(userProfileEditRequestDto.getNewPassword());

            } else {
                user.getPastPasswords().add(userProfileEditRequestDto.getNewPassword());
            }
        }

        return new UserProfileEditResponseDto(user);

    }

    public MessageResponseDto logout(Long userId) {
        User user = findUserById(userId);

        // 리프래시 토큰 초기화
        user.editRefreshToken(null);

        // 사용자 정보 저장
        userRepository.save(user);

        // SecurityContextHolder 초기화
        SecurityContextHolder.clearContext();

        return new MessageResponseDto("로그아웃이 완료되었습니다.");
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 프로필을 찾을 수 없습니다."));
    }

}