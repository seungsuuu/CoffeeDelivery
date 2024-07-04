package com.sparta.coffeedeliveryproject.entity;

import com.sparta.coffeedeliveryproject.dto.ReviewRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "reviewContent", nullable = false)
    private String reviewContent;

    @Column(name = "review_like_count")
    private Long reviewLikeCount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToMany(mappedBy = "review", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ReviewLike> reviewLikeList = new ArrayList<>();

    public Review(ReviewRequestDto requestDto, Cafe cafe, Order order, User user) {
        this.reviewContent = requestDto.getReviewContent();
        this.cafe = cafe;
        this.order = order;
        this.user = user;
        this.reviewLikeCount = 0L;
    }

    public void update(ReviewRequestDto requestDto) {
        this.reviewContent = requestDto.getReviewContent();
    }

    public void likeReview() {
        this.reviewLikeCount++;
    }

    public void unlikeReview() {
        this.reviewLikeCount--;
    }

}
