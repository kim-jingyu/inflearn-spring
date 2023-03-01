package com.example.itemservicedbpractice.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품을 수정할 때 사용하는 객체
 */
@Data
@NoArgsConstructor @AllArgsConstructor
public class ItemUpdateDto {
    private String itemName; // 상품 이름
    private Integer price; // 상품 가격
    private Integer quantity; // 상품 수량
}
