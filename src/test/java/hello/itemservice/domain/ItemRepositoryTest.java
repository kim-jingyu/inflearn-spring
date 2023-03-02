package hello.itemservice.domain;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.memory.MemoryItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @AfterEach
    void afterEach() {
        if (itemRepository instanceof MemoryItemRepository) {
            ((MemoryItemRepository)itemRepository).clearStore();
        }
    }

    @Test
    void save() {
        Item itemA = new Item("itemA", 10000, 10);

        Item savedItem = itemRepository.save(itemA);
        Item foundItem = null;

        if (itemRepository.findById(itemA.getId()).isPresent()){
            foundItem = itemRepository.findById(itemA.getId()).get();
        }
        assertThat(foundItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem() {
        Item itemA = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(itemA);
        Long savedItemId = savedItem.getId();

        ItemUpdateDto updateParam = new ItemUpdateDto("itemB", 4800, 68);
        itemRepository.update(savedItemId, updateParam);

        Item foundItem = itemRepository.findById(savedItemId).get();
        assertThat(foundItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(foundItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(foundItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

    @Test
    void findItems() {
        Item itemA = new Item("itemA-1", 1000, 10);
        Item itemA2 = new Item("itemA-2", 2000, 20);
        Item itemC = new Item("itemC", 3000, 30);

        itemRepository.save(itemA);
        itemRepository.save(itemA2);
        itemRepository.save(itemC);

        test(null, null, itemA, itemA2, itemC);
        test("", null, itemA, itemA2, itemC);

        test("itemA", null, itemA, itemA2);
        test("-2", null, itemA2);

        test(null, 1500, itemA);

        test("itemA", 1500, itemA);
    }

    private void test(String itemName, Integer maxPrice, Item... items) {
        List<Item> result = itemRepository.findAll(new ItemSearchCond(itemName, maxPrice));
        assertThat(result).containsExactly(items);
    }


}
