package hello.advanced.ch0_1_2.trace.strategy;

import hello.advanced.ch0_1_2.trace.strategy.code.Callback;
import hello.advanced.ch0_1_2.trace.strategy.code.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {
    @Test
    void callbackV1() {
        TimeLogTemplate template = new TimeLogTemplate();

        template.execute(new Callback() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });

        template.execute(() -> log.info("비즈니스 로직2 실행"));
    }
}
