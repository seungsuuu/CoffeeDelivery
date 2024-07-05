package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepositoryQuery {
    Page<Review> findReviewByUserLikes(Pageable pageable, Long userId);
    Long countReviewByUserLikes(Long userId);
}
