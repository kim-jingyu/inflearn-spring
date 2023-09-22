package hello.advanced.proxy.config;

import hello.advanced.proxy.app.withoutinterface.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV2Config {
    @Bean(name = "orderControllerV2")
    public OrderController orderController() {
        return new OrderController(orderService());
    }

    @Bean(name = "orderServiceV2")
    public OrderService orderService() {
        return new OrderService(orderRepository());
    }

    @Bean(name = "orderRepositoryV2")
    public OrderRepository orderRepository() {
        return new OrderRepository();
    }
}
