package hello.advanced.proxy.config;

import hello.advanced.ch0_1_2.trace.logtrace.LogTrace;
import hello.advanced.proxy.app.withinterface.*;
import hello.advanced.proxy.app.withinterface.proxy.OrderControllerInterfaceProxy;
import hello.advanced.proxy.app.withinterface.proxy.OrderRepositoryInterfaceProxy;
import hello.advanced.proxy.app.withinterface.proxy.OrderServiceInterfaceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {
    @Bean
    public OrderController orderControllerV1(LogTrace logTrace) {
        OrderControllerImpl controllerImpl = new OrderControllerImpl(orderServiceV1(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderService orderServiceV1(LogTrace logTrace) {
        OrderServiceImpl serviceImpl = new OrderServiceImpl(orderRepositoryV1(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    @Bean
    public OrderRepository orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryImpl repositoryImpl = new OrderRepositoryImpl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
    }
}
