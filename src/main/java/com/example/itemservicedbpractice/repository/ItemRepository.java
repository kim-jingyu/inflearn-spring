package com.example.itemservicedbpractice.repository;

import com.example.itemservicedbpractice.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);
    void update(Long itemId, ItemUpdateDto itemUpdateDto);
    Optional<Item> findById(Long id);
    List<Item> findAll(ItemSearchCond cond);
}
