package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.entity.Order;
import com.sparta.coffeedeliveryproject.entity.OrderStatus;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.repository.OrderRepository;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    //주문 상태 변경(완료)
    @Transactional
    public MessageResponseDto completeOrder(Long orderId) {
        Order order = findOrderById(orderId);
        order.updateOrderStatus(OrderStatus.DELIVERY_COMPLETE);
        return new MessageResponseDto("배달이 완료되었습니다.");
    }

    // 주문 상태 변경
    @Transactional
    public MessageResponseDto cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);
        order.updateOrderStatus(OrderStatus.DELIVERY_CANCEL);
        return new MessageResponseDto("주문이 취소되었습니다.");
    }

    //주문 삭제
    public MessageResponseDto deleteOrder(Long orderId) {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
        return new MessageResponseDto("주문이 삭제되었습니다.");
    }

    //id로 주문 찾기
    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
    }

    // 어드민인지 구분
    @Transactional
    public boolean isAdmin(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다."));

        for (UserRole role : user.getUserRoles()) {
            if ("ADMIN".equals(role.getRole())) {
                return true;
            }
        }
        return false;
    }

}
