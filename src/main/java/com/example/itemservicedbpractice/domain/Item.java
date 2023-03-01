package com.example.itemservicedbpractice.domain;

import lombok.*;

/**
 * 상품 자체를 나타내는 객체
 */
@Getter @Setter @ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class Item {
    private Long id; // 상품 번호 (key)
    private String itemName; // 상품 이름
    private Integer price; // 상품 가격
    private Integer quantity; // 상품 수량
}
