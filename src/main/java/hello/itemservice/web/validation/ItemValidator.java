package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) { // 해당 검증기를 지원하는지 여부 확인
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors bindingResult) { // 검증 대상 객체와 BindingResult
        Item item = (Item) target;

        //검증 로직
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.rejectValue("itemName", "required");
//        }
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            bindingResult.rejectValue("quantity","max", new Object[]{9999}, null);
        }

        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int totalPrice = item.getPrice() * item.getQuantity();
            if (totalPrice <10000){
                bindingResult.reject("totalPrice", new Object[]{10000, totalPrice}, null);
            }
        }
    }
}
