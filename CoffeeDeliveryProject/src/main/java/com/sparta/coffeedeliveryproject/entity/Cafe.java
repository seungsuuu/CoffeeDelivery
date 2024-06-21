package com.sparta.coffeedeliveryproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cafe")
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cafeId;

    @Column(name = "cafe_name", nullable = false, unique = true)
    private String cafeName;

    @Column(name = "cafe_info", nullable = false)
    private String cafeInfo;

    @Column(name = "cafe_address", nullable = false)
    private String cafeAddress;

    @Column(name = "cafe_like_count")
    private Long cafeLikeCount;

    public Cafe(String cafeName, String cafeInfo, String cafeAddress) {
        this.cafeName = cafeName;
        this.cafeInfo = cafeInfo;
        this.cafeAddress = cafeAddress;
        this.cafeLikeCount = 0L;
    }

    public void likeCafe() {
        this.cafeLikeCount ++;
    }

    public void unlikeCafe() {
        this.cafeLikeCount --;
    }

}
