package hello.advanced.proxy.app.withoutinterface;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepositoryV2) {
        this.orderRepository = orderRepositoryV2;
    }

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
