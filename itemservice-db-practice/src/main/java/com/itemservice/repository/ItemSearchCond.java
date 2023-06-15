package com.itemservice.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 검색 조건
 */
@Data
@NoArgsConstructor @AllArgsConstructor
public class ItemSearchCond {
    private String itemName; // 검색 상품 이름, like 검색
    private Integer maxPrice; // 검색 가격 제한 조건
}
