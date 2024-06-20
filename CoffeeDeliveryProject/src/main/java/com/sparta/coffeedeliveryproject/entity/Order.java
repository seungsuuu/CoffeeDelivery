package com.sparta.coffeedeliveryproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table
@NoArgsConstructor
public class Order extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private int orderPrice;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    // 컬렉션 객체임을 알려줌
    @ElementCollection
    // menuNames의 데이터를 menu_names 테이블에 저장 후에 order_id의 컬럼과 join
    @CollectionTable(name = "menu_names", joinColumns = @JoinColumn(name = "order_id"))
    // menuNames 테이블의 컬렉션 값이 menu_name로 저장
    @Column(name = "menu_name")
    private List<String> menuNames; // 메뉴 이름 리스트

    public Order(int orderPrice, OrderStatus orderStatus, User user, Cafe cafe, List<String> menuNames) {
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.user = user;
        this.cafe = cafe;
        this.menuNames = menuNames;
    }
}
