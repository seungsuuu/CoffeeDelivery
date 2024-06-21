package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    // 주문 상태 변경(완료)
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<MessageResponseDto> completeOrder(@PathVariable Long orderId) {
        MessageResponseDto responseDto = adminOrderService.completeOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 주문 상태 변경(취소)
    @PutMapping("/orders/cancel/{orderId}")
    public ResponseEntity<MessageResponseDto> cancelOrder(@PathVariable Long orderId) {
        MessageResponseDto responseDto = adminOrderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
