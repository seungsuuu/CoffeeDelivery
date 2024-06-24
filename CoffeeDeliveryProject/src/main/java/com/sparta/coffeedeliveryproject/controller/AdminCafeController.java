package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.CafeEditRequestDto;
import com.sparta.coffeedeliveryproject.dto.CafeRequestDto;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.AdminCafeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCafeController {

    private final AdminCafeService adminCafeService;

    @PostMapping("/cafes")
    public ResponseEntity<CafeResponseDto> createCafe(@Valid @RequestBody CafeRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (adminCafeService.isAdmin(userDetails)) {
            CafeResponseDto responseDto = adminCafeService.createCafe(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            throw new IllegalArgumentException("관리자만 사용할 수 있는 기능입니다.");
        }
    }

    @GetMapping("/cafes")
    public ResponseEntity<List<CafeResponseDto>> getAllCafe(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (adminCafeService.isAdmin(userDetails)) {
            List<CafeResponseDto> responseDtoList = adminCafeService.getAllCafe(page - 1);
            return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
        } else {
            throw new IllegalArgumentException("관리자만 사용할 수 있는 기능입니다.");
        }
    }

    @PutMapping("/cafes/{cafeId}")
    public ResponseEntity<CafeResponseDto> editCafe(@PathVariable(value = "cafeId") Long cafeId,
                                                    @RequestBody CafeEditRequestDto requestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (adminCafeService.isAdmin(userDetails)) {
            CafeResponseDto responseDto = adminCafeService.editCafe(cafeId, requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            throw new IllegalArgumentException("관리자만 사용할 수 있는 기능입니다.");
        }
    }

    @DeleteMapping("/cafes/{cafeId}")
    public ResponseEntity<MessageResponseDto> deleteCafe(@PathVariable(value = "cafeId") Long cafeId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (adminCafeService.isAdmin(userDetails)) {
            MessageResponseDto responseDto = adminCafeService.deleteCafe(cafeId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            throw new IllegalArgumentException("관리자만 사용할 수 있는 기능입니다.");
        }
    }

}
