package com.itemservice.service;

import com.itemservice.domain.Item;
import com.itemservice.repository.ItemSearchCond;
import com.itemservice.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item save(Item item);

    void update(Long itemId, ItemUpdateDto itemUpdateDto);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearchCond);
}
