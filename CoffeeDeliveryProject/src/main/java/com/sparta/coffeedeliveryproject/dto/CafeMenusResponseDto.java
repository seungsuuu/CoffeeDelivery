package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.Cafe;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeMenusResponseDto {

    private Long cafeId;

    private String cafeName;

    private String cafeInfo;

    private String cafeAddress;

    private Long cafeLikeCount;

    private List<MenuResponseDto> menus;

    public CafeMenusResponseDto(Cafe cafe, List<MenuResponseDto> menus) {
        this.cafeId = cafe.getCafeId();
        this.cafeName = cafe.getCafeName();
        this.cafeInfo = cafe.getCafeInfo();
        this.cafeAddress = cafe.getCafeAddress();
        this.cafeLikeCount = cafe.getCafeLikeCount();
        this.menus = menus;
    }

}
