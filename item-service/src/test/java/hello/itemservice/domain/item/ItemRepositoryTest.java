package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    private ItemRepository itemRepository = new ItemRepository();
    @AfterEach
    void init() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("A", 1000, 10);

        //when
        Item savedItem = itemRepository.saveItem(item);

        //then
        Item foundItem = itemRepository.findItemById(savedItem.getId());
        assertThat(savedItem).isEqualTo(foundItem);
    }

    @Test
    void findAllItems() {
        //given
        Item itemA = new Item("A", 1000, 10);
        itemRepository.saveItem(itemA);
        Item itemB = new Item("B", 2000, 20);
        itemRepository.saveItem(itemB);
        Item itemC = new Item("C", 3000, 30);

        //when
        List<Item> items = itemRepository.findAllItems();

        //then
        assertThat(items).contains(itemA, itemB);
    }

    @Test
    void updateItem() {
        //given
        Item itemA = new Item("A", 1000, 10);
        Item savedItem = itemRepository.saveItem(itemA);

        //when
        Item paramItem = new Item("B", 2000, 20);
        itemRepository.updateItem(savedItem.getId(), paramItem);

        //then
        assertThat(savedItem.getItemName()).isEqualTo(paramItem.getItemName());
    }
}