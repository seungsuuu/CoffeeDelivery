package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.CafeRequestDto;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCafeService {

    private final CafeRepository cafeRepository;

    public AdminCafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public CafeResponseDto createCafe(CafeRequestDto requestDto) {

        String cafeName = requestDto.getCafeName();
        String cafeInfo = requestDto.getCafeInfo();
        String cafeAddress = requestDto.getCafeAddress();
        Cafe cafe = new Cafe(cafeName, cafeInfo, cafeAddress);

        Cafe SaveCafe = cafeRepository.save(cafe);

        return new CafeResponseDto(SaveCafe);
    }

    public List<CafeResponseDto> getAllCafe(int page) {

        Sort sort = Sort.by(Sort.Direction.ASC, "cafeId");
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<CafeResponseDto> CafePage = cafeRepository.findAll(pageable).map(CafeResponseDto::new);
        List<CafeResponseDto> responseDtoList = CafePage.getContent();

        if (responseDtoList.isEmpty()) {
            throw new IllegalArgumentException("작성된 카페 페이지가 없거나, 입력된 " + (page + 1) + " 페이지에 글이 없습니다.");
        }

        return responseDtoList;
    }

}
