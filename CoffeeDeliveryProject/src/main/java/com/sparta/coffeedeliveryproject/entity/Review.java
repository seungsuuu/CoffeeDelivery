package com.sparta.coffeedeliveryproject.entity;

import com.sparta.coffeedeliveryproject.dto.ReviewRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "reviewContent", nullable = false)
    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "review_like_count")
    private int reviewLikeCount;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public Review(ReviewRequestDto requestDto, Cafe cafe, Order order, User user){
        this.reviewContent = requestDto.getReviewContent();
        this.cafe = cafe;
        this.order = order;
        this.user = user;
        this.reviewLikeCount = 0;
    }

    public void update(ReviewRequestDto requestDto){
        this.reviewContent = requestDto.getReviewContent();
        this.modifiedAt = LocalDateTime.now();
    }

    public void likeReview() {
        this.reviewLikeCount++;
    }

    public void unlikeReview() {
        this.reviewLikeCount--;
    }

}
