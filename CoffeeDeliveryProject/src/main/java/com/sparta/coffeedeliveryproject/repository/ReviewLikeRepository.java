package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByReviewReviewIdAndUserUserId(Long reviewId, Long userId);
}
