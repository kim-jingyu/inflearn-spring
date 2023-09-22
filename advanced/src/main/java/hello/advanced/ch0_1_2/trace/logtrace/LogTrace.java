package hello.advanced.ch0_1_2.trace.logtrace;

import hello.advanced.ch0_1_2.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
