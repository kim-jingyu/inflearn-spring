package hello.itemservice.domain;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.memory.MemoryItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Transactional 애노테이션을 테스트에서 사용하면,
 * 스프링은 테스트를 트랜잭션 안에서 실행하고, 테스트가 끝나면 트랜잭션을 자동으로 rollback 시켜 버린다.
 */
//@Commit
//@Rollback(value = false)
@Transactional
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    // 트랜잭션 관련 코드
//    @Autowired
//    PlatformTransactionManager transactionManager;
//    TransactionStatus status;
//
//    @BeforeEach
//    void beforeEach() {
//        // 트랜잭션 시작
//        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//    }

    @AfterEach
    void afterEach() {
        // MemoryItemRepository 를 사용하는 경우, 제한적으로 사용
        if (itemRepository instanceof MemoryItemRepository) {
            ((MemoryItemRepository)itemRepository).clearStore();
        }

        // 트랜잭션 롤백
//        transactionManager.rollback(status);
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
