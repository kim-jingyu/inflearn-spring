package practice.core2.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

//        ThreadA: A 사용자 10000원 주문
        statefulService1.order("userA",10000);
//        ThreadB: B 사용자 20000원 주문
        statefulService2.order("userB", 20000);

//        ThreadA: A 사용자 주문 금액 조회 (기댓값 : 10000원)
        int price = statefulService1.getPrice();

//        기댓값 10000원과 다르게 20000원 출력
        System.out.println("price = " + price);
    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}