package hello.advanced.app;

import hello.advanced.trace.TraceInfo;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final HelloTraceV1 trace;

    public void orderItem(TraceInfo traceInfo, String itemId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceInfo, "OrderService.orderItem()");
            orderRepository.save(status.getTraceInfo(), itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
