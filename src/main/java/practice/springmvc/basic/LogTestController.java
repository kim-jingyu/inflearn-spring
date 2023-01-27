package practice.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String data = "hello-spring!";

        log.trace("trace log={}", data);
        log.debug("debug log={}", data);
        log.info("info log={}", data);
        log.warn("warn log={}", data);
        log.error("error log={}", data);

        return "ok";
    }
}
