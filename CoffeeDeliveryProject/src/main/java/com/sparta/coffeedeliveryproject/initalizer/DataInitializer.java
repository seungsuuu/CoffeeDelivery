package com.sparta.coffeedeliveryproject.initalizer;

import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import com.sparta.coffeedeliveryproject.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRoleRepository.count() == 0) {

            // 유저 역할 초기 데이터
            UserRole userRole = new UserRole("USER");
            UserRole adminRole = new UserRole("ADMIN");

            userRoleRepository.save(userRole);
            userRoleRepository.save(adminRole);
        }

        if (userRepository.count() == 0) {

            // 유저 초기 데이터
            UserRole userRole = userRoleRepository.findByRole("USER")
                    .orElseThrow(() -> new RuntimeException("Role not found: USER"));

            UserRole adminRole = userRoleRepository.findByRole("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role not found: ADMIN"));

            Set<UserRole> user1Roles = new HashSet<>();
            user1Roles.add(userRole);

            Set<UserRole> user2Roles = new HashSet<>();
            user2Roles.add(userRole);

            Set<UserRole> user3Roles = new HashSet<>();
            user3Roles.add(userRole); // 관리자는 USER 역할과 ADMIN 역할을 모두 가짐
            user3Roles.add(adminRole);

            User user1 = new User("user1", "password123", "NickName1", user1Roles, UserStatusEnum.ACTIVE, "refresh_token_1");
            User user2 = new User("user2", "password12223", "NickName2", user2Roles, UserStatusEnum.ACTIVE, "refresh_token_1");
            User user3 = new User("user3", "password123", "NickName3", user3Roles, UserStatusEnum.ACTIVE, "refresh_token_1");

            // 유저 role 추가되는 것이 가능한지 테스트
            user2.addUserRoles(adminRole);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        }
    }
}
