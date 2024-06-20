package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.Order;
import com.sparta.coffeedeliveryproject.entity.OrderStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponseDto {

    List<OrderRequestDto> orders;

    int orderPrice;

    OrderStatus orderStatus;

    public OrderResponseDto(List<OrderRequestDto> orders, int orderPrice, OrderStatus orderStatus) {
        this.orders = orders;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }

}
