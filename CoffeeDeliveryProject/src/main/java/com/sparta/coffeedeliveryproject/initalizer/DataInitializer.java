package com.sparta.coffeedeliveryproject.initalizer;

import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRoleRepository.count() == 0) {

            // 유저 역할 초기 데이터
            UserRole userRole = new UserRole("USER");
            UserRole adminRole = new UserRole("ADMIN");

            userRoleRepository.save(userRole);
            userRoleRepository.save(adminRole);
        }
    }
}
