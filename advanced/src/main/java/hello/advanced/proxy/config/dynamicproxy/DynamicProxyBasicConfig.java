package hello.advanced.proxy.config.dynamicproxy;

import hello.advanced.ch0_1_2.trace.logtrace.LogTrace;
import hello.advanced.proxy.app.withinterface.*;
import hello.advanced.proxy.config.dynamicproxy.handler.LogTraceBasicHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfig {
    @Bean
    public OrderController orderControllerV1(LogTrace logTrace) {
        OrderController orderController = new OrderControllerImpl(orderServiceV1(logTrace));

        OrderController proxy = (OrderController) Proxy.newProxyInstance(OrderController.class.getClassLoader(), new Class[]{OrderController.class}, new LogTraceBasicHandler(orderController, logTrace));
        return proxy;
    }

    @Bean
    public OrderService orderServiceV1(LogTrace logTrace) {
        OrderService orderService = new OrderServiceImpl(orderRepositoryV1(logTrace));

        OrderService proxy = (OrderService) Proxy.newProxyInstance(OrderService.class.getClassLoader(), new Class[]{OrderService.class}, new LogTraceBasicHandler(orderService, logTrace));
        return proxy;
    }

    @Bean
    public OrderRepository orderRepositoryV1(LogTrace logTrace) {
        OrderRepository orderRepository = new OrderRepositoryImpl();

        OrderRepository proxy = (OrderRepository) Proxy.newProxyInstance(OrderRepository.class.getClassLoader(), new Class[]{OrderRepository.class}, new LogTraceBasicHandler(orderRepository, logTrace));
        return proxy;
    }
}
