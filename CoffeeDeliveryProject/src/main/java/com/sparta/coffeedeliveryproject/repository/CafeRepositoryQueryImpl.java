package com.sparta.coffeedeliveryproject.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.QCafe;
import com.sparta.coffeedeliveryproject.entity.QCafeLike;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CafeRepositoryQueryImpl implements CafeRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Cafe> findCafeByUserLikes(Pageable pageable, Long userId) {

        QCafe cafe = QCafe.cafe;
        QCafeLike cafeLike = QCafeLike.cafeLike;

        JPAQuery<Cafe> query = jpaQueryFactory.selectFrom(cafe)
                .leftJoin(cafe.cafeLikeList, cafeLike)
                .where(cafeLike.user.userId.eq(userId));

        long count = countCafeByUserLikes(userId);

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order order : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(cafe.getType(), cafe.getMetadata());
            OrderSpecifier orderSpecifier = new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(order.getProperty()));
            query.orderBy(orderSpecifier);
        }

        List<Cafe> cafeList = query.fetch();

        return PageableExecutionUtils.getPage(cafeList, pageable, () -> count);
    }

    @Override
    public Long countCafeByUserLikes(Long userId) {
        QCafe qCafe = QCafe.cafe;
        return jpaQueryFactory.select(Wildcard.count)
                .from(qCafe)
                .where(qCafe.cafeLikeList.any().user.userId.eq(userId))
                .fetch()
                .get(0);
    }

}
