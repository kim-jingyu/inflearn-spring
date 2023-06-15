package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 트랜잭션 - 내부 호출 문제 발생되는 코드
 */
@SpringBootTest
@Slf4j
public class InternalCallProblem {
    static class CallService {
        public void external() {
            log.info("call external Method");
            log.info("external 메소드 트랜잭션 적용 여부 = {}", TransactionSynchronizationManager.isActualTransactionActive());
            internal();
        }

        @Transactional
        public void internal() {
            log.info("call internal Method");
            log.info("internal 메소드 트랜잭션 적용 여부 = {}", TransactionSynchronizationManager.isActualTransactionActive());
        }
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        CallService callService() {
            return new CallService();
        }
    }

    @Autowired
    CallService callService;

    @Test
    void AopCheck() {
        log.info("AOP 적용 여부 = {}", AopUtils.isAopProxy(callService));
    }

    @Test
    void externalCall() {
        callService.external();
    }

    @Test
    void internalCall() {
        callService.internal();
    }
}
