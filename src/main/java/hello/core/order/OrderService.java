package hello.core.order;

/**
 * 클라이언트(Controller)에서 회원 ID,상품명,상품 가격을 파라미터로 주문 서비스에 넘기면 주문 결과를 반환한다.
 */
public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
