package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.ReviewLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
