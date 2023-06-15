package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV2 {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    // 시작
    public TraceStatus begin(String message) {
        TraceId trace = new TraceId();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", trace.getId(), addSpace(START_PREFIX, trace.getLevel()),message);
        return new TraceStatus(trace, startTimeMs, message);
    }

    // 동기화하여 시작
    public TraceStatus beginSync(TraceId beforeTraceId, String message) {
        TraceId nextId = beforeTraceId.createNextId();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", nextId.getId(), addSpace(START_PREFIX, nextId.getLevel()), message);
        return new TraceStatus(nextId, startTimeMs, message);
    }

    // 종료
    public void end(TraceStatus status) {
        complete(status, null);
    }

    // 예외
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        TraceId trace = status.getTraceId();
        long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        if (e == null) {
            log.info("[{}] {}{} {}ms", trace.getId(), addSpace(COMPLETE_PREFIX, trace.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} {}ms ex={}", trace.getId(), addSpace(EX_PREFIX, trace.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }


    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == (level - 1)) ? "|" + prefix : "|  ");
        }
        return sb.toString();
    }
}
