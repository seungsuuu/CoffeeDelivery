package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.UserRoleMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleMatchRepository extends JpaRepository<UserRoleMatch, Long> {
}
