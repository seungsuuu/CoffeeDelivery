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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

//    public ReviewResponseDto getReview(Long cafeId, ReviewRequestDto requestDto) {
//
//        Cafe cafe = findCafeById(cafeId);
//        Review review = new Review(requestDto, cafe);
//        Review getReview = reviewRepository.findAll(review);
//
//        return new ReviewResponseDto(review);
//
//    }

//    public List<ReviewResponseDto> getReview() {
//
//        return reviewRepository.findAllByReviewByCreatedAtDesc().stream().map(ReviewResponseDto::new).toList();
//    }

    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto/*, User user*/) {

        Review review = findReviewById(reviewId);
        review.update(requestDto);
        return new ReviewResponseDto(review);
        /*
        if(review.getUser().getId() == user.getId()){
            review.update(requestDto);
            return new ReviewResponseDto(review);
         }else throw new IllegalArgumentException("본인이 작성한 리뷰만 수정 가능합니다.");
        */
    }

    private Review findReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

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
