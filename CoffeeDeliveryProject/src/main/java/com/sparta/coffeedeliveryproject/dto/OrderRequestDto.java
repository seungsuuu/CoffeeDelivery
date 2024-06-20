package com.sparta.coffeedeliveryproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    @NotBlank
    private Long menuId;
}
