package com.itemservice.config;

import com.itemservice.repository.ItemRepository;
import com.itemservice.repository.memory.MemoryItemRepository;
import com.itemservice.service.ItemService;
import com.itemservice.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }
}
