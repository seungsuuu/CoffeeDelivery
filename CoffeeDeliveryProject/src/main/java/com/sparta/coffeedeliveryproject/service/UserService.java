package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.*;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import com.sparta.coffeedeliveryproject.exceptions.PasswordMismatchException;
import com.sparta.coffeedeliveryproject.exceptions.RecentlyUsedPasswordException;
import com.sparta.coffeedeliveryproject.jwt.JwtUtil;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

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

    public MessageResponseDto login(LogingRequestDto logingRequestDto, HttpServletResponse response) {
        // 사용자의 아이디, 비밀번호를 받아온다.
        String userName = logingRequestDto.getUserName();
        String password = logingRequestDto.getPassword();
        // 회원가입이 된 사용자인지 확인.
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 회원가입 시 입력한 비밀번호와 로그인시 입력한 비밀번호가 일치하지 않을 경우
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 액세스 토큰, 리프레쉬 토큰 생성
        String accessToken = jwtUtil.createAccessToken(user.getUserName(), UserStatusEnum.ACTIVE);
        String refreshToken = jwtUtil.createRefreshToken(user);
        // header에 액세스토큰 저장
        jwtUtil.addAccessJwtToHeader(accessToken, response);
        // DB에 리프레쉬토큰 저장
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new MessageResponseDto("로그인이 완료되었습니다.");
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

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 프로필을 찾을 수 없습니다."));
    }

}