package com.sparta.coffeedeliveryproject.initalizer;

import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.entity.UserStatus;
import com.sparta.coffeedeliveryproject.repository.UserRoleRepository;
import com.sparta.coffeedeliveryproject.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    private final UserStatusRepository userStatusRepository;

    @Override
    public void run(String... args) throws Exception {
        if(userRoleRepository.count() == 0) {

            // 유저 역할 초기 데이터
            UserRole userRole = new UserRole();
            userRole.setRole("USER");


            // 관리자 역할 초기 데이터
            UserRole adminRole = new UserRole();
            adminRole.setRole("ADMIN");

            userRoleRepository.save(userRole);
            userRoleRepository.save(adminRole);

        }

        if(userStatusRepository.count() == 0) {

            // 정상 상태
            UserStatus activeStatus = new UserStatus();
            activeStatus.setStatus("ACTIVE");

            // 차단 상태
            UserStatus blockStatus = new UserStatus();
            blockStatus.setStatus("BLOCK");

            userStatusRepository.save(activeStatus);
            userStatusRepository.save(blockStatus);
        }
    }
}
