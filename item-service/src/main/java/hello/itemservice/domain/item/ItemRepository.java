package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 상품 저장소
 */
@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    public Item saveItem(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findItemById(Long id) {
        return store.get(id);
    }

    public List<Item> findAllItems() {
        return new ArrayList<>(store.values());
    }

    public void updateItem(Long itemId, Item paramItem) {
        Item foundItem = findItemById(itemId);
        foundItem.setItemName(paramItem.getItemName());
        foundItem.setPrice(paramItem.getPrice());
        foundItem.setQuantity(paramItem.getQuantity());
        foundItem.setOpen(paramItem.getOpen());
        foundItem.setRegions(paramItem.getRegions());
        foundItem.setItemType(paramItem.getItemType());
        foundItem.setDeliveryCode(paramItem.getDeliveryCode());
    }

    public void clearStore() {
        store.clear();
    }
}
