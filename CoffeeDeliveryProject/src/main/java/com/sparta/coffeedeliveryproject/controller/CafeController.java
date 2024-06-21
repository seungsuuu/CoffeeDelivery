package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.CafeRequestDto;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.service.CafeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CafeController {

    private final CafeService cafeService;

    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @PostMapping("/admin/cafes")
    public ResponseEntity<CafeResponseDto> createCafe(@RequestBody CafeRequestDto requestDto) {

        CafeResponseDto responseDto = cafeService.createCafe(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
