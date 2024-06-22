package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewRequestDto {

    private String reviewContent;

}
