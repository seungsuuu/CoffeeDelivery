package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.CafeRequestDto;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import org.springframework.stereotype.Service;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
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
}
