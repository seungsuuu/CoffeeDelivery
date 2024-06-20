package com.sparta.coffeedeliveryproject.initalizer;

import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.entity.UserRoleMatch;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import com.sparta.coffeedeliveryproject.repository.UserRoleMatchRepository;
import com.sparta.coffeedeliveryproject.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    private final UserRepository userRepository;

    private final UserRoleMatchRepository userRoleMatchRepository; // UserRoleMatch의 Repository 추가

    @Override
    public void run(String... args) throws Exception {

        if (userRoleRepository.count() == 0) {

            // 유저 역할 초기 데이터
            UserRole userRole = new UserRole();
            userRole.setRole("USER");

            // 관리자 역할 초기 데이터
            UserRole adminRole = new UserRole();
            adminRole.setRole("ADMIN");

            userRoleRepository.save(userRole);
            userRoleRepository.save(adminRole);
        }

        if (userRepository.count() == 0) {

            // 유저 초기 데이터
            UserRole userRole = userRoleRepository.findByRole("USER")
                    .orElseThrow(() -> new RuntimeException("Role not found: USER"));

            UserRole adminRole = userRoleRepository.findByRole("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role not found: ADMIN"));

            User user = new User("user1", "password123", "NickName1", userRole, UserStatusEnum.ACTIVE, "refresh_token_1");

            User user2 = new User("user2", "password12223", "NickName2", userRole, UserStatusEnum.ACTIVE, "refresh_token_1");

            User user3 = new User("user3", "password123", "NickName3", adminRole, UserStatusEnum.ACTIVE, "refresh_token_1");

            userRepository.save(user);

            userRepository.save(user2);

            userRepository.save(user3);

            // UserRoleMatch 엔티티 초기화 및 저장
            UserRoleMatch match1 = new UserRoleMatch();

            match1.setUser(user);

            match1.setUserRole(userRole);

            UserRoleMatch match2 = new UserRoleMatch();

            match2.setUser(user2);

            match2.setUserRole(userRole);

            UserRoleMatch match3 = new UserRoleMatch();

            match3.setUser(user3);

            match3.setUserRole(adminRole);

            userRoleMatchRepository.save(match1);

            userRoleMatchRepository.save(match2);

            userRoleMatchRepository.save(match3);

        }
    }
}
