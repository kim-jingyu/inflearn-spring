package hello.advanced.proxy.app.withinterface;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderControllerImpl implements OrderController{
    private final OrderService orderService;

    public OrderControllerImpl(OrderService orderServiceV1) {
        this.orderService = orderServiceV1;
    }

    @Override
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @Override
    public String noLog() {
        return "ok";
    }
}
