package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryQuery {

    List<Review> findAllByCafeCafeId(Long cafeId);
    Review findByOrderOrderId(Long orderId);

}
