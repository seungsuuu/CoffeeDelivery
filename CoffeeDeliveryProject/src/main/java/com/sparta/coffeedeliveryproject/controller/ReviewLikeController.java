package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.ReviewLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cafes")
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

    //리뷰 좋아요
    @PostMapping("/reviews/{reviewId}/likes")
    public ResponseEntity<MessageResponseDto> likeReview(@PathVariable Long reviewId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDto responseDto = reviewLikeService.likeReview(reviewId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    //리뷰 좋아요 취소
    @DeleteMapping("/reviews/{reviewId}/likes")
    public ResponseEntity<MessageResponseDto> unLikeReview(@PathVariable Long reviewId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MessageResponseDto responseDto = reviewLikeService.unlikeReview(reviewId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
