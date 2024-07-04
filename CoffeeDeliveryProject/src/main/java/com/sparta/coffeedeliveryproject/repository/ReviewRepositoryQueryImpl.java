package com.sparta.coffeedeliveryproject.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.coffeedeliveryproject.entity.QReview;
import com.sparta.coffeedeliveryproject.entity.QReviewLike;
import com.sparta.coffeedeliveryproject.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryQueryImpl implements ReviewRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Review> findReviewByUserLikes(Pageable pageable, Long userId) {

        QReview review = QReview.review;
        QReviewLike reviewLike = QReviewLike.reviewLike;

        JPAQuery<Review> query = jpaQueryFactory.selectFrom(review)
                .leftJoin(review.reviewLikeList, reviewLike)
                .where(reviewLike.user.userId.eq(userId));

        long count = countQuery(userId).fetch().get(0);

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order order : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(review.getType(), review.getMetadata());
            OrderSpecifier orderSpecifier = new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(order.getProperty()));
            query.orderBy(orderSpecifier);
        }

        List<Review> reviewList = query.fetch();

        return PageableExecutionUtils.getPage(reviewList, pageable, () -> count);
    }

    private JPAQuery<Long> countQuery(Long userId) {
        QReview qReview = QReview.review;
        return jpaQueryFactory.select(Wildcard.count)
                .from(qReview)
                .where(qReview.reviewLikeList.any().user.userId.eq(userId));
    }

}
