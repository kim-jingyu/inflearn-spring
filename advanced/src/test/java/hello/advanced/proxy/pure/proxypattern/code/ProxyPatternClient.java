package hello.advanced.proxy.pure.proxypattern.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyPatternClient {
    private Subject subject;

    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        log.info("result = {}", subject.operation());
        long stopTime = System.currentTimeMillis();
        log.info("endtime = {}ms", stopTime - startTime);
    }
}
