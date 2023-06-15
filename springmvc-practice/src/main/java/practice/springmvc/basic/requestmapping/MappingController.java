package practice.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * /hello-basic URL 호출이 오면 이 메서드가 실행되도록 매핑한다.
     * 대부분의 속성을 배열로 제공하므로 다중 설정이 가능하다.
     * {"/hello-basic","/hello-go"}
     *
     * @return HTTP 메시지 바디에 바로 입력
     */
    @RequestMapping({"/hello-basic", "/hello-go"})
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    @PostMapping(value = "/mapping-post-v2")
    public String mappingPostV2() {
        log.info("mappingPostV2");
        return "ok";
    }

    @GetMapping("/mapping-path-v1/{userId}")
    public String mappingPathV1(@PathVariable("userId") String data) {
        log.info("mappingPathV1 userId={}", data);
        return "ok";
    }

    @GetMapping("/mapping-path-v2/{userId}")
    public String mappingPathV2(@PathVariable String userId) {
        log.info("mappingPathV2 userId={}", userId);
        return "ok";
    }

    @GetMapping("/mapping-path-v3/users/{userId}/orders/{orderId}")
    public String mappingPathV3(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPathV3 userid={}, order={}", userId, orderId);
        return "ok";
    }

    @GetMapping(value = "/mapping-param", params = {"mode=debug", "data=good"})
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    @GetMapping(value = "/mapping-header", headers = {"name=coco", "age=12"})
    public String mappingHeaders() {
        log.info("mappingHeaders");
        return "ok";
    }

    @PostMapping(value = "/mapping-content-type", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    @PostMapping(value = "/mapping-accept", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
