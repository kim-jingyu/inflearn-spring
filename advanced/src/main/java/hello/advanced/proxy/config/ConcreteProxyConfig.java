package hello.advanced.proxy.config;

import hello.advanced.ch0_1_2.trace.logtrace.LogTrace;
import hello.advanced.proxy.app.withoutinterface.OrderController;
import hello.advanced.proxy.app.withoutinterface.OrderRepository;
import hello.advanced.proxy.app.withoutinterface.OrderService;
import hello.advanced.proxy.app.withoutinterface.proxy.OrderControllerConcreteProxy;
import hello.advanced.proxy.app.withoutinterface.proxy.OrderRepositoryConcreteProxy;
import hello.advanced.proxy.app.withoutinterface.proxy.OrderServiceConcreteProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {
    @Bean
    public OrderController orderControllerV2(LogTrace logTrace) {
        OrderController orderController = new OrderController(orderServiceV2(logTrace));
        return new OrderControllerConcreteProxy(orderController, logTrace);
    }

    @Bean
    public OrderService orderServiceV2(LogTrace logTrace) {
        OrderService orderService = new OrderService(orderRepositoryV2(logTrace));
        return new OrderServiceConcreteProxy(orderService, logTrace);
    }

    @Bean
    public OrderRepository orderRepositoryV2(LogTrace logTrace) {
        OrderRepository orderRepository = new OrderRepository();
        return new OrderRepositoryConcreteProxy(orderRepository, logTrace);
    }
}
