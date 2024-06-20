package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.dto.OrderRequestDto;
import com.sparta.coffeedeliveryproject.dto.OrderResponseDto;
import com.sparta.coffeedeliveryproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 작성
    @PostMapping("/cafes/{cafeId}/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable Long cafeId,
                                                        @RequestBody List<OrderRequestDto> orders
            /*@AuthenticationPrincipal UserDetailsImpl userDetails*/) {
        OrderResponseDto responseDto = orderService.createOrder(cafeId, orders/*, userDetails.getUser()*/);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    /*//주문 조회
    @GetMapping("/cafes/orders")
    public List<OrderResponseDto> getOrders(@AuthenticationPrincipal UserDetailsImp userDetails) {
        return orderService.getOrders(userDetails.getUser());
    }*/

    // 주문 상태 변경(완료)
    @PutMapping("/admin/orders/{orderId}")
    public ResponseEntity<MessageResponseDto> completeOrder(@PathVariable Long orderId) {
        MessageResponseDto responseDto = orderService.completeOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 주문 상태 변경(취소)
    @PutMapping("/admin/orders/cancel/{orderId}")
    public ResponseEntity<MessageResponseDto> cancelOrder(@PathVariable Long orderId) {
        MessageResponseDto responseDto = orderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


}
