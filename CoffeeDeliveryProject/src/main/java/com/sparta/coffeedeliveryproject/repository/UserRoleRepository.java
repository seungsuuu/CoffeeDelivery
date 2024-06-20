package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
