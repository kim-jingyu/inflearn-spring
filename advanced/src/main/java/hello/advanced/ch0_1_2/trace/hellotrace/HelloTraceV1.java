package hello.advanced.ch0_1_2.trace.hellotrace;

import hello.advanced.ch0_1_2.trace.TraceInfo;
import hello.advanced.ch0_1_2.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV1 {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    public TraceStatus begin(String message) {
        TraceInfo traceInfo = new TraceInfo();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceInfo.getId(), addSpace(START_PREFIX, traceInfo.getLevel()), message);
        return new TraceStatus(traceInfo, startTimeMs, message);
    }

    public TraceStatus beginSync(TraceInfo beforeTraceId, String message) {
        TraceInfo nextInfo = beforeTraceId.createNextLevelInfo();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", nextInfo.getId(), addSpace(START_PREFIX, nextInfo.getLevel()), message);
        return new TraceStatus(nextInfo, startTimeMs, message);
    }

    public void end(TraceStatus status) {
        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceInfo traceInfo = status.getTraceInfo();

        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceInfo.getId(), addSpace(COMPLETE_PREFIX, traceInfo.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceInfo.getId(), addSpace(EX_PREFIX, traceInfo.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|  ");
        }

        return sb.toString();
    }
}
