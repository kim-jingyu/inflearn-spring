package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloTraceV1Test {
    @Test
    void begin_end() {
        // given
        HelloTraceV1 trace = new HelloTraceV1();

        // when
        TraceStatus status = trace.begin("hello");

        // then
        trace.end(status);
    }

    @Test
    void begin_ex() {
        // given
        HelloTraceV1 trace = new HelloTraceV1();

        // when
        TraceStatus status = trace.begin("hello");

        // then
        trace.exception(status, new IllegalStateException());
    }
}