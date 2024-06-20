package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
