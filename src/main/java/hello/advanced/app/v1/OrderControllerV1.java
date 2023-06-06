package hello.advanced.app.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderControllerV1 {
    private final OrderServiceV1 orderService;

    @GetMapping("/v1/request")
    public String request(@RequestParam String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }
}
