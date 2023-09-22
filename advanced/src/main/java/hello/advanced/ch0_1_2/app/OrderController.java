package hello.advanced.ch0_1_2.app;


import hello.advanced.ch0_1_2.trace.callback.TraceTemplate;
import hello.advanced.ch0_1_2.trace.logtrace.LogTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final TraceTemplate template;

    public OrderController(OrderService orderService, LogTrace trace) {
        this.orderService = orderService;
        this.template = new TraceTemplate(trace);
    }

    @GetMapping("/request")
    public String request(@RequestParam String itemId) {
        return template.execute("OrderController.request()", () -> {
            orderService.orderItem(itemId);
            return "ok";
        });
    }
}
