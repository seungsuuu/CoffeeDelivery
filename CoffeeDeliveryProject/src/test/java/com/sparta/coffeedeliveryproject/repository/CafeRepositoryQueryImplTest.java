package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.config.TestJpaConfig;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.CafeLike;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
class CafeRepositoryQueryImplTest {

    User saveUser;
    Cafe saveCafe;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private CafeLikeRepository cafeLikeRepository;

    private void userSetup() {

        String userName = "tmdtn015";
        String password = "@Tmdtn013";
        String nickName = "김승수유저2";
        UserRole userRole = new UserRole("USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);

        User user = new User(userName, password, nickName);
        user.setUserRoles(userRoles);

        saveUser = userRepository.save(user);
    }

    private void cafeSetup() {

        String cafeName = "카페이름";
        String cafeInfo = "카페정보";
        String cafeAddress = "카페주소";

        Cafe cafe = new Cafe(cafeName, cafeInfo, cafeAddress);

        saveCafe = cafeRepository.save(cafe);
    }


    @Test
    @DisplayName("QueryDsl findByUserLikes 테스트")
    void findByUserLikes() {

        //given
        userSetup();
        cafeSetup();

        Long userId = saveUser.getUserId();
        Long cafeId = saveCafe.getCafeId();
        String sortBy = "createdAt";
        int page = 0; //page 값은 0부터 시작
        int pageSize = 5;

        CafeLike cafeLike = new CafeLike(saveUser, saveCafe);
        cafeLikeRepository.save(cafeLike);

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        //when
        Page<Cafe> cafePage = cafeRepository.findCafeByUserLikes(pageable, userId);
        List<Cafe> cafeList = cafePage.getContent();

        //then
        assertNotNull(cafePage);
        assertEquals(pageSize, cafePage.getSize());
        for (Cafe actualCafe : cafeList) {
            if (cafeId.equals(actualCafe.getCafeId())) {
                assertEquals(saveCafe.getCafeName(), actualCafe.getCafeName());
                assertEquals(saveCafe.getCafeInfo(), actualCafe.getCafeInfo());
                assertEquals(saveCafe.getCafeAddress(), actualCafe.getCafeAddress());
            }
        }
    }
}