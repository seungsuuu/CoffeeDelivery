package com.sparta.coffeedeliveryproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CafeRequestDto {

    @NotBlank(message = "[cafeName:blank] 카페 이름을 작성해주세요!")
    private String cafeName;

    @NotBlank(message = "[cafeInfo:blank] 카페 소개을 작성해주세요!")
    private String cafeInfo;

    @NotBlank(message = "[cafeAddress:blank] 카페 주소를 작성해주세요!")
    private String cafeAddress;

}
