package hello.itemservice.domain.item;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * 상품 객체
 */
@Getter
@Setter
public class Item {

    private Long id; // 상품 번호

    @NotBlank
    private String itemName; // 상품 이름

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price; // 상품 가격

    @NotNull
    @Max(9999)
    private Integer quantity; // 상품 수량

    private Boolean open; // 판매 여부
    private List<String> regions; // 등록 지역
    private ItemType itemType; // 상품 종류
    private String deliveryCode; // 배송 방식

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
