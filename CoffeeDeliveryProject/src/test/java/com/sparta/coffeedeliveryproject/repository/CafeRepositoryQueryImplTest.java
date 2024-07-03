package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.config.TestJpaConfig;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.entity.Cafe;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
class CafeRepositoryQueryImplTest {

    @Autowired
    private CafeRepository cafeRepository;

    @Test
    void findByUserLikes() {

        //given
        Long userId = 2L;
        Long CafeId = 2L;
        String sortBy = "createdAt";
        int page = 1;
        int pageSize = 5;

        Cafe cafe = cafeRepository.findById(CafeId).orElse(null);
        CafeResponseDto ExpectResponseDto = new CafeResponseDto(cafe);

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        //when
        Page<CafeResponseDto> cafePage = cafeRepository.findByUserLikes(pageable, userId).map(CafeResponseDto::new);
        List<CafeResponseDto> responseDtoList = cafePage.getContent();

        //then
        assertNotNull(cafePage);
        assertEquals(pageSize, cafePage.getSize());
        for (CafeResponseDto responseDto : responseDtoList) {
            if (responseDto.getCafeId().equals(CafeId)) {
                assertEquals(ExpectResponseDto.getCafeId(), responseDto.getCafeId());
                assertEquals(ExpectResponseDto.getCafeName(), responseDto.getCafeName());
                assertEquals(ExpectResponseDto.getCafeInfo(), responseDto.getCafeInfo());
                assertEquals(ExpectResponseDto.getCafeAddress(), responseDto.getCafeAddress());
            }
        }
    }
}