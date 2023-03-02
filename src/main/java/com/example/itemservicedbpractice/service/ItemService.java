package com.example.itemservicedbpractice.service;

import com.example.itemservicedbpractice.domain.Item;
import com.example.itemservicedbpractice.repository.ItemSearchCond;
import com.example.itemservicedbpractice.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item save(Item item);

    void update(Long itemId, ItemUpdateDto itemUpdateDto);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearchCond);
}
