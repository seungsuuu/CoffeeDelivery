package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
