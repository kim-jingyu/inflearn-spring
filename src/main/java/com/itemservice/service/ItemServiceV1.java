package com.itemservice.service;

import com.itemservice.domain.Item;
import com.itemservice.repository.ItemRepository;
import com.itemservice.repository.ItemSearchCond;
import com.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 대부분의 기능을 단순히 ItemRepository 에 위임한다.
 */
@Service
@RequiredArgsConstructor
public class ItemServiceV1 implements ItemService{
    private final ItemRepository itemRepository;
    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto itemUpdateDto) {
        itemRepository.update(itemId, itemUpdateDto);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findItems(ItemSearchCond itemSearchCond) {
        return itemRepository.findAll(itemSearchCond);
    }
}
