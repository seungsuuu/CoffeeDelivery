package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.config.TestJpaConfig;
import com.sparta.coffeedeliveryproject.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
class ReviewRepositoryQueryImplTest {

    User createUser;
    User likeReviewUser;
    Cafe saveCafe;
    Order saveOrder1;
    Order saveOrder2;
    Review saveReview1;
    Review saveReview2;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewLikeRepository reviewLikeRepository;

    private void userSetup() {

        String userName1 = "tmdtn016";
        String password1 = "@Tmdtn013";
        String nickName1 = "김승수유저3";
        UserRole userRole1 = new UserRole("USER");
        Set<UserRole> userRoles1 = new HashSet<>();
        userRoles1.add(userRole1);

        String userName2 = "tmdtn017";
        String password2 = "@Tmdtn013";
        String nickName2 = "김승수유저4";
        UserRole userRole2 = new UserRole("USER");
        Set<UserRole> userRoles2 = new HashSet<>();
        userRoles2.add(userRole2);

        User user1 = new User(userName1, password1, nickName1);
        User user2 = new User(userName2, password2, nickName2);
        user1.setUserRoles(userRoles1);
        user2.setUserRoles(userRoles2);

        createUser = userRepository.save(user1);
        likeReviewUser = userRepository.save(user2);
    }

    private void cafeSetup() {

        String cafeName = "카페이름";
        String cafeInfo = "카페정보";
        String cafeAddress = "카페주소";

        Cafe cafe = new Cafe(cafeName, cafeInfo, cafeAddress);

        saveCafe = cafeRepository.save(cafe);
    }

    private void orderSetup() {

        int orderPrice1 = 7500;
        OrderStatus orderStatus1 = OrderStatus.DELIVERY_START;
        List<String> menuNames1 = new ArrayList<>();
        menuNames1.add("아메리카노");
        menuNames1.add("라때");
        menuNames1.add("아이스티");

        int orderPrice2 = 4500;
        OrderStatus orderStatus2 = OrderStatus.DELIVERY_START;
        List<String> menuNames2 = new ArrayList<>();
        menuNames2.add("아메리카노");
        menuNames2.add("라때");

        Order order1 = new Order(orderPrice1, orderStatus1, createUser, saveCafe, menuNames1);
        Order order2 = new Order(orderPrice2, orderStatus2, createUser, saveCafe, menuNames2);

        saveOrder1 = orderRepository.save(order1);
        saveOrder2 = orderRepository.save(order2);
    }

    private void reviewSetup() {

        String reviewContent1 = "맛있습니다1";
        String reviewContent2 = "맛있습니다2";

        Review review1 = new Review(reviewContent1, saveCafe, saveOrder1, createUser);
        Review review2 = new Review(reviewContent2, saveCafe, saveOrder2, createUser);

        saveReview1 = reviewRepository.save(review1);
        saveReview2 = reviewRepository.save(review2);
    }

    @Test
    @DisplayName("QueryDsl findReviewByUserLikes 테스트")
    void findReviewByUserLikes() {

        //given
        userSetup();
        cafeSetup();
        orderSetup();
        reviewSetup();

        Long userId = likeReviewUser.getUserId();
        String sortBy = "createdAt";
        int page = 0; //page 값은 0부터 시작
        int pageSize = 5;

        ReviewLike reviewLike1 = new ReviewLike(likeReviewUser, saveReview1);
        ReviewLike reviewLike2 = new ReviewLike(likeReviewUser, saveReview2);

        reviewLikeRepository.save(reviewLike1);
        reviewLikeRepository.save(reviewLike2);

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        //when
        Page<Review> reviewPage = reviewRepository.findReviewByUserLikes(pageable, userId);
        List<Review> reviewList = reviewPage.getContent();

        //then
        assertNotNull(reviewPage);
        assertEquals(pageSize, reviewPage.getSize());
        assertEquals(2, reviewList.size());
        for (Review actualReview : reviewList) {
            if (saveReview1.getReviewId().equals(actualReview.getReviewId())) {
                assertEquals(saveReview1.getReviewContent(), actualReview.getReviewContent());
            } else if (saveReview2.getReviewId().equals(actualReview.getReviewId())) {
                assertEquals(saveReview2.getReviewContent(), actualReview.getReviewContent());
            }
        }
    }

    @Test
    @DisplayName("QueryDsl countReviewByUserLikes 테스트")
    void countReviewByUserLikes() {

        //given
        userSetup();
        cafeSetup();
        orderSetup();
        reviewSetup();

        Long userId = likeReviewUser.getUserId();

        ReviewLike reviewLike1 = new ReviewLike(likeReviewUser, saveReview1);
        ReviewLike reviewLike2 = new ReviewLike(likeReviewUser, saveReview2);

        reviewLikeRepository.save(reviewLike1);
        reviewLikeRepository.save(reviewLike2);

        //when
        Long actualCount = reviewRepository.countReviewByUserLikes(userId);

        //then
        assertEquals(2L, actualCount);
    }

}