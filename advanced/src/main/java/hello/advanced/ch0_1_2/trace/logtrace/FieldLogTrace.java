package hello.advanced.ch0_1_2.trace.logtrace;

import hello.advanced.ch0_1_2.trace.TraceStatus;
import hello.advanced.ch0_1_2.trace.TraceInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private TraceInfo traceInfoHolder;

    @Override
    public TraceStatus begin(String message) {
        syncTraceInfo();
        TraceInfo traceInfo = traceInfoHolder;
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceInfo.getId(), addSpace(START_PREFIX, traceInfo.getLevel()), message);
        return new TraceStatus(traceInfo, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void syncTraceInfo() {
        if (traceInfoHolder == null) {
            traceInfoHolder = new TraceInfo();
        } else {
            traceInfoHolder = traceInfoHolder.createNextLevelInfo();
        }
    }

    private void releaseTraceId() {
        if (traceInfoHolder.isFirstLevel()) {
            traceInfoHolder = null;
        } else {
            traceInfoHolder = traceInfoHolder.createPreviousLevelInfo();
        }
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

        releaseTraceId();
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|  ");
        }

        return sb.toString();
    }
}
