package hello.advanced.ch0_1_2.app;

import hello.advanced.ch0_1_2.trace.callback.TraceTemplate;
import hello.advanced.ch0_1_2.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final TraceTemplate template;

    public OrderService(OrderRepository orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        template.execute("OrderService.orderItem()", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }
}
