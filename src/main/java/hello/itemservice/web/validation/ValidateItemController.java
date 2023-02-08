package hello.itemservice.web.validation;

import hello.itemservice.domain.item.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 폼 전송 객체 사용 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/validation/items")
@RequiredArgsConstructor
public class ValidateItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAllItems();

        model.addAttribute("items", items);
        return "validation/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findItemById(itemId);

        model.addAttribute("item", item);
        return "validation/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        //특정 필드 예외가 아닌 전체 예외 ( 글로벌 오류 추가 - Object error )
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPrice", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("bindingResult-addForm={}", bindingResult);
            return "validation/addForm";
        }

        //성공 로직
        Item item = new Item(form.getItemName(), form.getPrice(), form.getQuantity());

        Item savedItem = itemRepository.saveItem(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findItemById(itemId);
        model.addAttribute("item", item);
        return "validation/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult) {
        //특정 필드가 아닌 전체 예외 ( 글로벌 오류 - object error )
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPrice", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 수정 폼으로
        if (bindingResult.hasErrors()) {
            log.info("bindingResult-editForm={}", bindingResult);
            return "validation/editForm";
        }

        //성공 로직
        Item item = new Item(form.getItemName(), form.getPrice(), form.getQuantity());

        itemRepository.updateItem(itemId, item);
        return "redirect:/validation/items/{itemId}";
    }

    @PostConstruct
    public void init() {
        itemRepository.saveItem(new Item("A", 1000, 10));
        itemRepository.saveItem(new Item("B", 2000, 20));
    }
}
