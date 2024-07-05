package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepositoryQuery {
    Page<Cafe> findCafeByUserLikes(Pageable pageable, Long userId);
    Long countCafeByUserLikes(Long userId);
}
