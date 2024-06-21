package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.CafeLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeLikeRepository extends JpaRepository<CafeLike, Long> {
    Optional<CafeLike> findByCafeIdAndUserId(Long cafeId, Long userId);
}
