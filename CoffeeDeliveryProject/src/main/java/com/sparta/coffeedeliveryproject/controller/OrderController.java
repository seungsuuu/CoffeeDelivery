package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.OrderRequestDto;
import com.sparta.coffeedeliveryproject.dto.OrderResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafes")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 작성
    @PostMapping("/{cafeId}/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable Long cafeId,
                                                        @RequestBody List<OrderRequestDto> orders,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        OrderResponseDto responseDto = orderService.createOrder(cafeId, orders, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    //주문 조회 (시큐리티 개발되면 주석 풀 예정입니다)
    @GetMapping("/orders")
    public List<OrderResponseDto> getOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.getOrders(userDetails.getUser());
    }

}
