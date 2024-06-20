package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
}
