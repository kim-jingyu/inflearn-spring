package hello.advanced.proxy.app.withoutinterface.proxy;

import hello.advanced.ch0_1_2.trace.TraceStatus;
import hello.advanced.ch0_1_2.trace.logtrace.LogTrace;
import hello.advanced.proxy.app.withoutinterface.OrderRepository;

public class OrderRepositoryConcreteProxy extends OrderRepository {
    private final OrderRepository target;
    private final LogTrace logTrace;

    public OrderRepositoryConcreteProxy(OrderRepository target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderRepository.save()");
            target.save(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
