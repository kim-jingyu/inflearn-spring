package com.itemservice.web;

import com.itemservice.domain.Item;
import com.itemservice.repository.ItemSearchCond;
import com.itemservice.repository.ItemUpdateDto;
import com.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 상품을 CRUD 하는 컨트롤러
 */
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /**
     * @return 상품 목록 뷰
     */
    @GetMapping
    public String items(@ModelAttribute ItemSearchCond searchCond, Model model) {
        List<Item> items = itemService.findItems(searchCond);
        model.addAttribute("items", items);
        return "form/items";
    }

    /**
     * @return 상품 상세 뷰
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemService.findById(itemId).orElseThrow();
        model.addAttribute("item", item);
        return "form/item";
    }

    /**
     * @return 상품 등록 폼
     */
    @GetMapping("/add")
    public String addForm() {
        return "form/addForm";
    }

    /**
     * 상품 등록
     * @return
     */
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemService.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    /**
     * @return 상품 수정 폼
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId).orElseThrow();
        model.addAttribute("item", item);
        return "editForm";
    }

    /**
     * 상품 수정
     * @return
     */
    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @ModelAttribute ItemUpdateDto updateParam) {
        itemService.update(itemId, updateParam);
        return "redirect:/items/{itemId}";
    }
}
