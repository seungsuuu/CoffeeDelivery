package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.entity.Review;
import com.sparta.coffeedeliveryproject.entity.ReviewLike;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.repository.ReviewLikeRepository;
import com.sparta.coffeedeliveryproject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰 좋아요
    @Transactional
    public MessageResponseDto likeReview(Long reviewId, User user) {
        Review review = findReviewById(reviewId);

        if (review.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("자신의 리뷰에 좋아요를 할 수 없습니다.");
        }

        if (reviewLikeRepository.findByReviewReviewIdAndUserUserId(reviewId, user.getUserId()).isPresent()) {
            throw new IllegalArgumentException("중복 좋아요는 할 수 없습니다.");
        } else {
            review.likeReview();
            reviewLikeRepository.save(new ReviewLike(user, review));
        }
        return new MessageResponseDto("좋아요 성공");
    }

    //리뷰 좋아요 취소
    @Transactional
    public MessageResponseDto unlikeReview(Long reviewId, User user) {
        Review review = findReviewById(reviewId);

        ReviewLike reviewLike = reviewLikeRepository.findByReviewReviewIdAndUserUserId(reviewId, user.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글에 좋아요를 하지 않았습니다."));
        review.unlikeReview();
        reviewLikeRepository.delete(reviewLike);
        return new MessageResponseDto("좋아요 취소");
    }

    //id로 리뷰 찾기
    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
    }

}
