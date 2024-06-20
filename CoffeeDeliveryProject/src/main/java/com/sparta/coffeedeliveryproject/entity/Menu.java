package com.sparta.coffeedeliveryproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private Long menuPrice;

    // 밑에 주석 3개는 아직 Cafe Entity가 구현되지 않아 맵핑 부분에 오류가 있어서 주석처리했습니다.
//    @JoinColumn(name = "cafe_id", nullable = false)
//    private Cafe cafe;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    public Menu(String menuName, Long menuPrice, Long cafeId) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cafeId = cafeId;
//        this.cafe = cafe;
    }

}
