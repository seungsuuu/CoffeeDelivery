package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.dto.ReviewRequestDto;
import com.sparta.coffeedeliveryproject.dto.ReviewResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafes")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{cafeId}/orders/{orderId}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable Long cafeId,
                                                          @PathVariable Long orderId,
                                                          @Valid @RequestBody ReviewRequestDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ReviewResponseDto responseDto = reviewService.createReview(cafeId, orderId, requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{cafeId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviewCafe(@PathVariable Long cafeId) {

        List<ReviewResponseDto> responseDtoList = reviewService.getReviewCafe(cafeId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long reviewId,
                                                          @Valid @RequestBody ReviewRequestDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDto> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        MessageResponseDto responseDto = reviewService.deleteReview(reviewId, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
