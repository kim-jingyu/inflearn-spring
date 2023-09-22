package hello.advanced.proxy.app.withinterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface OrderController {
    // LogTrace를 적용할 대상
    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    // LogTrace를 적용하지 않을 대상
    @GetMapping("/v1/no-log")
    String noLog();
}
