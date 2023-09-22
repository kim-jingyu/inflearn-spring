package hello.advanced.ch0_1_2.trace.logtrace;

import hello.advanced.ch0_1_2.trace.TraceStatus;
import hello.advanced.ch0_1_2.trace.logtrace.FieldLogTrace;
import org.junit.jupiter.api.Test;

class FieldLogTraceTest {
    @Test
    void begin_end_level2() {
        // given
        FieldLogTrace trace = new FieldLogTrace();

        // when && then
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_ex_level2() {
        // given
        FieldLogTrace trace = new FieldLogTrace();

        // when && then
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }
}