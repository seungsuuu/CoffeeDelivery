package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
