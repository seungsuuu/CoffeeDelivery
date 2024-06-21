package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.Cafe;
import lombok.Getter;

@Getter
public class CafeResponseDto {

    private Long cafeId;

    private String cafeName;

    private String cafeInfo;

    private String cafeAddress;

    private Long cafeLikeCount;

    public CafeResponseDto(Cafe cafe) {
        this.cafeId = cafe.getCafeId();
        this.cafeName = cafe.getCafeName();
        this.cafeInfo = cafe.getCafeInfo();
        this.cafeAddress = cafe.getCafeAddress();
        this.cafeLikeCount = cafe.getCafeLikeCount();
    }

}
