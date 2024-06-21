package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.dto.UserEditRequestDto;
import com.sparta.coffeedeliveryproject.dto.UserResponseDto;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.exceptions.PasswordMismatchException;
import com.sparta.coffeedeliveryproject.exceptions.RecentlyUsedPasswordException;
import com.sparta.coffeedeliveryproject.exceptions.UserNotFoundException;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @Transactional
    public UserResponseDto editUser(Long userId, UserEditRequestDto userEditRequestDto) {

        User user = findUserById(userId);

        if (userEditRequestDto.getNewUserName() != null) {
            user.editUserName(user.getUserName());
        }

        if (userEditRequestDto.getNewNickName() != null) {
            user.editUserNickName(userEditRequestDto.getNewNickName());
        }

        // 현재 비밀번호 확인
        if (userEditRequestDto.getPassword() != null) {
            log.info(user.getPastPasswords().toString());

            // TODO: 나중에 password 암호화되면 encoder 추가 필요
            // 일치하지 않으면
            if (!userEditRequestDto.getPassword().matches(user.getPassword())) {
                throw new PasswordMismatchException("현재 비밀번호가 일치하지 않습니다.");
            }

            // 이전 비밀번호에 새로운 비밀번호가 있으면
            if (user.getPastPasswords().contains(userEditRequestDto.getNewPassword())) {
                throw new RecentlyUsedPasswordException("최근 설정한 4개의 비밀번호로는 비밀번호를 변경하실 수 없습니다.");
            }

            // 새로운 비밀번호 설정
            user.editUserPassword(userEditRequestDto.getNewPassword());

            // 과거 비밀번호 저장하는 리스트의 갯수가 4개인지 확인
            if (user.getPastPasswords().stream().count() == 4) {

                // 가장 먼저 저장된 password 삭제
                user.getPastPasswords().remove(0);

                // 새로운 비밀번호 저장
                user.getPastPasswords().add(userEditRequestDto.getNewPassword());

            } else {
                user.getPastPasswords().add(userEditRequestDto.getNewPassword());
            }
        }

        return new UserResponseDto(user);
    }

    @Transactional
    public MessageResponseDto deleteUser(Long userId) {
        User user = findUserById(userId);

        userRepository.delete(user);

        return new MessageResponseDto("유저가 삭제되었습니다.");
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("현재 userId로 유저를 찾을 수 없습니다."));
    }
}
