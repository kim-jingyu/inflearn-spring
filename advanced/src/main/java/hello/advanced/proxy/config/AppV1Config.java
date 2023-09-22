package hello.advanced.proxy.config;

import hello.advanced.proxy.app.withinterface.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV1Config {
    @Bean(name = "orderControllerV1")
    public OrderController orderControllerV1() {
        return new OrderControllerImpl(orderServiceV1());
    }

    @Bean(name = "orderServiceV1")
    public OrderService orderServiceV1() {
        return new OrderServiceImpl(orderRepositoryV1());
    }

    @Bean(name = "orderRepositoryV1")
    public OrderRepository orderRepositoryV1() {
        return new OrderRepositoryImpl();
    }
}
