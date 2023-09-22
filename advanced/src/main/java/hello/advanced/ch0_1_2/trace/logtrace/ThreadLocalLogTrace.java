package hello.advanced.ch0_1_2.trace.logtrace;

import hello.advanced.ch0_1_2.trace.TraceStatus;
import hello.advanced.ch0_1_2.trace.TraceInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private ThreadLocal<TraceInfo> traceInfoHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceInfo();
        TraceInfo traceInfo = traceInfoHolder.get();
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
        TraceInfo traceInfo = traceInfoHolder.get();
        if (traceInfo == null) {
            traceInfoHolder.set(new TraceInfo());
        } else {
            traceInfoHolder.set(traceInfo.createNextLevelInfo());
        }
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|  ");
        }
        return sb.toString();
    }

    private void complete(TraceStatus status, Exception e) {
//        TraceInfo traceInfo = status.getTraceInfo();
        TraceInfo traceInfo = traceInfoHolder.get();
        long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();

        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceInfo.getId(), addSpace(COMPLETE_PREFIX, traceInfo.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceInfo.getId(), addSpace(EX_PREFIX, traceInfo.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }

        releaseTraceInfo();
    }

    private void releaseTraceInfo() {
        TraceInfo traceInfo = traceInfoHolder.get();
        if (traceInfo.isFirstLevel()) {
            traceInfoHolder.remove();
        } else {
            traceInfoHolder.set(traceInfo.createPreviousLevelInfo());
        }
    }
}
