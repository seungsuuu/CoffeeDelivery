package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.SignupRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserProfileEditRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserProfileEditResponseDto;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.exceptions.PasswordMismatchException;
import com.sparta.coffeedeliveryproject.exceptions.RecentlyUsedPasswordException;
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