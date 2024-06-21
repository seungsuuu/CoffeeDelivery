package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.CafeRequestDto;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.service.AdminCafeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminCafeController {

    private final AdminCafeService adminCafeService;

    public AdminCafeController(AdminCafeService adminCafeService) {
        this.adminCafeService = adminCafeService;
    }

    @PostMapping("/cafes")
    public ResponseEntity<CafeResponseDto> createCafe(@RequestBody CafeRequestDto requestDto) {

        CafeResponseDto responseDto = adminCafeService.createCafe(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
