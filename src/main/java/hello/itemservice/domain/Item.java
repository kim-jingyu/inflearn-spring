package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity // JPA 가 사용하는 객체라는 뜻
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 10)
    private String itemName;
    private Integer price;
    private Integer quantity;

    /**
     * JPA 는 public 또는 protected 기본 생성자가 필수이다.
     */
    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
