package com.example.itemservicedbpractice.repository.memory;

import com.example.itemservicedbpractice.domain.Item;
import com.example.itemservicedbpractice.repository.ItemRepository;
import com.example.itemservicedbpractice.repository.ItemSearchCond;
import com.example.itemservicedbpractice.repository.ItemUpdateDto;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Item save(Item item) {
        item.setId(++sequence); // 상품 번호 할당
        store.put(item.getId(), item); // 메모리 store 에 저장
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto itemUpdateDto) {
        Item foundItem = findById(itemId).orElseThrow(()->new IllegalArgumentException("그런 상품은 없습니다."));
        foundItem.setItemName(itemUpdateDto.getItemName());
        foundItem.setPrice(itemUpdateDto.getPrice());
        foundItem.setQuantity(itemUpdateDto.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName(); // 검색 조건 상품명
        Integer maxPrice = cond.getMaxPrice(); // 검색 조건 제한 가격
        return store.values().stream()
                .filter(item -> {
                    if (ObjectUtils.isEmpty(item.getItemName())) {
                        return true;
                    }
                    return item.getItemName().contains(itemName);
                }).filter(item -> {
                    if (maxPrice == null) {
                        return true;
                    }
                    return item.getPrice() <= maxPrice;
                }).collect(Collectors.toList());
    }

    public void clearStore() {
        store.clear();
    }
}
