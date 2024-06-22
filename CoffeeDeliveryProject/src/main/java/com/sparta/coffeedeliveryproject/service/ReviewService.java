package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.ReviewRequestDto;
import com.sparta.coffeedeliveryproject.dto.ReviewResponseDto;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.Order;
import com.sparta.coffeedeliveryproject.entity.Review;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import com.sparta.coffeedeliveryproject.repository.OrderRepository;
import com.sparta.coffeedeliveryproject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;
    private final OrderRepository orderRepository;

    public ReviewResponseDto createReview(Long cafeId, Long orderId,ReviewRequestDto requestDto/*,User user*/){
        Cafe cafe = findCafeById(cafeId);
        Order order = findOrderById(orderId);
        Review review = new Review(requestDto, cafe, order);
        Review saveReview = reviewRepository.save(review);
        return new ReviewResponseDto(review);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
    }

    private Cafe findCafeById(Long cafeId) {
        return cafeRepository.findById(cafeId).orElseThrow(() ->
                new IllegalArgumentException("해당 카페를 찾을 수 없습니다."));
    }

}
