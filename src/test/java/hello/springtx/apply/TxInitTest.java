package hello.springtx.apply;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 트랜잭션 AOP 주의 사항 - 초기화 시점
 */
@Slf4j
@SpringBootTest
public class TxInitTest {
    static class Hello {
        @PostConstruct
        @Transactional
        public void initPostConstruct() {
            log.info("initPostConstruct 메서드 호출");
            log.info("initByPostConstruct 트랜잭션 적용 여부 = {}", TransactionSynchronizationManager.isActualTransactionActive());
        }

        @EventListener(value = ApplicationReadyEvent.class)
        @Transactional
        public void initApplicationReadyEvent() {
            log.info("initApplicationReadyEvent 메서드 호출");
            log.info("initApplicationReadyEvent 트랜잭션 적용 여부 = {}", TransactionSynchronizationManager.isActualTransactionActive());
        }
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        Hello hello() {
            return new Hello();
        }
    }

    @Autowired
    Hello hello;

    @Test
    void initTest() {
        // 초기화 코드는 스프링이 초기화 시점에 호출한다.
    }
}
