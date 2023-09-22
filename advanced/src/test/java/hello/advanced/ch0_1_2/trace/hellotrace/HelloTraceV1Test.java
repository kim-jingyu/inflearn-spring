package hello.advanced.ch0_1_2.trace.hellotrace;

import hello.advanced.ch0_1_2.trace.TraceStatus;
import hello.advanced.ch0_1_2.trace.hellotrace.HelloTraceV1;
import org.junit.jupiter.api.Test;

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