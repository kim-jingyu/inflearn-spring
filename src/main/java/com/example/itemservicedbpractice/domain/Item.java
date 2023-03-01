package com.example.itemservicedbpractice.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 상품 자체를 나타내는 객체
 */
@Getter @Setter @ToString @EqualsAndHashCode
public class Item {
    private String itemName; // 상품 이름
    private Integer price; // 상품 가격
    private Integer quantity; // 상품 수량

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
