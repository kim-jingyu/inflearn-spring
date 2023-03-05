package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MyBatisItemRepository 는 단순히 ItemMapper 에 기능을 위임
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class MyBatisItemRepository implements ItemRepository {
    private final ItemMapper itemMapper;
    @Override
    public Item save(Item item) {
        log.info("MyBatis save 메서드 동작! = {}", item);
        log.info("itemMapper class = {}", itemMapper.getClass()); // 동적 프록시 기술 적용 확인
        itemMapper.save(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        log.info("MyBatis update 메서드 동작! = {}, {}", itemId, updateParam);
        itemMapper.update(itemId, updateParam);
    }

    @Override
    public Optional<Item> findById(Long id) {
        log.info("MyBatis findById 메서드 동작! = {}", id);
        return itemMapper.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        log.info("MyBatis findAll 메서드 동작! = {}", cond);
        return itemMapper.findAll(cond);
    }
}
