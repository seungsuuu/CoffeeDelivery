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
