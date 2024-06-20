package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.OrderStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponseDto {

    private List<String> menuNames;

    private int orderPrice;

    private OrderStatus orderStatus;

    public OrderResponseDto(List<String> menuNames, int orderPrice, OrderStatus orderStatus) {
        this.menuNames = menuNames;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }

}
