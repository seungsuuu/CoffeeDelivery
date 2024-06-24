package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.OrderRequestDto;
import com.sparta.coffeedeliveryproject.dto.OrderResponseDto;
import com.sparta.coffeedeliveryproject.entity.*;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import com.sparta.coffeedeliveryproject.repository.MenuRepository;
import com.sparta.coffeedeliveryproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final CafeRepository cafeRepository;

    //주문 작성
    public OrderResponseDto createOrder(Long cafeId, List<OrderRequestDto> orders, User user) {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("주문 목록이 비어 있습니다.");
        }

        List<String> menuNames = getOrderNames(orders);
        Cafe cafe = findCafeById(cafeId);

        Order order = new Order(getOrderTotalPrice(orders), OrderStatus.DELIVERY_START, user, cafe, menuNames);
        orderRepository.save(order);

        return new OrderResponseDto(menuNames, order.getOrderId(), getOrderTotalPrice(orders), OrderStatus.DELIVERY_START);
    }

    //주문 조회 (시큐리티 개발되면 주석 풀 예정입니다)
    public List<OrderResponseDto> getOrders(User user) {
        List<Order> orders = orderRepository.findByUserUserId(user.getUserId());

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDto orderResponseDto = new OrderResponseDto(
                    order.getMenuNames(),
                    order.getOrderId(),
                    order.getOrderPrice(),
                    order.getOrderStatus()
            );
            orderResponseDtos.add(orderResponseDto);
        }
        if (!orders.isEmpty()) {
            return orderResponseDtos;
        } else {
            throw new IllegalArgumentException("주문 목록이 비어있습니다.");
        }

    }

    //id로 메뉴 찾기
    private Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() ->
                new IllegalArgumentException("해당 메뉴을 찾을 수 없습니다."));
    }

    //id로 카페 찾기
    private Cafe findCafeById(Long cafeId) {
        return cafeRepository.findById(cafeId).orElseThrow(() ->
                new IllegalArgumentException("해당 카페를 찾을 수 없습니다."));
    }

    //주문 리스트로 총 주문 가격 구하기
    private int getOrderTotalPrice(List<OrderRequestDto> orderRequestDtoList) {
        int orderPrice = 0;
        for (OrderRequestDto orderRequestDto : orderRequestDtoList) {
            orderPrice += findMenuById(orderRequestDto.getMenuId()).getMenuPrice();
        }
        return orderPrice;
    }

    //메뉴 리스트에서 주문 이름 목록 추출
    private List<String> getOrderNames(List<OrderRequestDto> orders) {
        List<String> orderNames = new ArrayList<>();
        for (OrderRequestDto order : orders) {
            Menu menu = findMenuById(order.getMenuId());
            orderNames.add(menu.getMenuName());
        }
        return orderNames;
    }

}
