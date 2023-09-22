package hello.advanced.ch0_1_2.trace;

import lombok.Getter;

@Getter
public class TraceStatus {
    private TraceInfo traceInfo;
    private Long startTimeMs;
    private String message;

    public TraceStatus(TraceInfo traceInfo, Long startTimeMs, String message) {
        this.traceInfo = traceInfo;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }
}
