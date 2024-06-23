package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>{

    // findAllByCafeId로 하면 오류 발생해서 'findAllByCafeCafeId' 로 작성
    // findAllByCafeCafeId 이게 안 된다면 'findAllByCafeId' 로 변경해서 사용해주세요
    List<Review> findAllByCafeCafeId(Long cafeId);

}
