package hello.advanced.ch0_1_2.trace.template.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 변하지 않는 부분
 */
@Slf4j
public abstract class AbstractTemplate {
    public void execute() {
        long startTime = System.currentTimeMillis();

        call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    protected abstract void call();
}
