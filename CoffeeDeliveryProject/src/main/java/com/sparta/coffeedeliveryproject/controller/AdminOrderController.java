package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    // 주문 상태 변경(완료)
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<MessageResponseDto> completeOrder(@PathVariable Long orderId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (adminOrderService.isAdmin(userDetails)) {
            MessageResponseDto responseDto = adminOrderService.completeOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            return ResponseEntity.status(403).body(new MessageResponseDto("어드민만 가능한 기능입니다."));
        }
    }

    // 주문 상태 변경(취소)
    @PutMapping("/orders/cancel/{orderId}")
    public ResponseEntity<MessageResponseDto> cancelOrder(@PathVariable Long orderId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (adminOrderService.isAdmin(userDetails)) {
            MessageResponseDto responseDto = adminOrderService.cancelOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            return ResponseEntity.status(403).body(new MessageResponseDto("어드민만 가능한 기능입니다."));
        }
    }

    // 주문 삭제
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<MessageResponseDto> deleteOrder(@PathVariable Long orderId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (adminOrderService.isAdmin(userDetails)) {
            MessageResponseDto responseDto = adminOrderService.deleteOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            return ResponseEntity.status(403).body(new MessageResponseDto("어드민만 가능한 기능입니다."));
        }
    }

}
