package hello.springtx.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static hello.springtx.order.PayStatus.*;
import static hello.springtx.order.Username.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceTest(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @Test
    void 정상_프로세스() throws NotEnoughMoneyException {
        Order order = new Order();
        order.setUsername(정상);

        orderService.order(order);

        Order foundOrder = orderRepository.findById(order.getId()).get();
        assertThat(foundOrder.getPayStatus()).isEqualTo(완료);
    }

    /**
     * 런타임 예외로 롤백이 수행되어서 Order 데이터가 비어있다.
     */
    @Test
    void 런타임_예외_발생() {
        Order order = new Order();
        order.setUsername(예외);

        assertThatThrownBy(() -> orderService.order(order))
                .isInstanceOf(RuntimeException.class);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        assertThat(foundOrder.isEmpty()).isTrue();
    }

    /**
     * 체크 예외로 커밋이 수행되었기 때문에 Order 데이터가 저장된다.
     */
    @Test
    void 잔고부족() {
        Order order = new Order();
        order.setUsername(잔고부족);

        try {
            orderService.order(order);
            Assertions.fail("잔고 부족 예외가 발생해야 한다.");
        } catch (NotEnoughMoneyException e) {
            log.info("고객에게 잔고 부족을 알리고 별도의 계좌로 입금하도록 안내한다.");
        }

        Order foundOrder = orderRepository.findById(order.getId()).get();
        assertThat(foundOrder.getPayStatus()).isEqualTo(대기);
    }
}