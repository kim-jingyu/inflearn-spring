package hello.advanced.proxy.app.withoutinterface.proxy;

import hello.advanced.ch0_1_2.trace.TraceStatus;
import hello.advanced.ch0_1_2.trace.logtrace.LogTrace;
import hello.advanced.proxy.app.withoutinterface.OrderService;

public class OrderServiceConcreteProxy extends OrderService {
    private final OrderService target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderService target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem()");
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
