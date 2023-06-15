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
 * 트랜잭션 - 내부 호출 문제 해결
 * 별도의 클래스로 분리
 */
@Slf4j
@SpringBootTest
public class InternalCallSolution {
    static class ExternalService {
        @Autowired
        InternalService internalService;

        public void external() {
            log.info("external 메서드 호출");
            log.info("external 메서드 트랜잭션 적용 여부 = {}", TransactionSynchronizationManager.isActualTransactionActive());
            internalService.internal();
        }
    }

    static class InternalService {
        @Transactional
        public void internal() {
            log.info("internal 메서드 호출");
            log.info("internal 메서드 트랜잭션 적용 여부 = {}", TransactionSynchronizationManager.isActualTransactionActive());
        }
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        ExternalService externalService() {
            return new ExternalService();
        }

        @Bean
        InternalService internalService() {
            return new InternalService();
        }
    }

    @Autowired
    ExternalService externalService;
    @Autowired
    InternalService internalService;

    @Test
    void AopCheck() {
        log.info("AopUtils.isAopProxy(externalService) = {}", AopUtils.isAopProxy(externalService));
        log.info("AopUtils.isAopProxy(internalService) = {}", AopUtils.isAopProxy(internalService));
    }

    @Test
    void internalCall() {
        internalService.internal();
    }

    @Test
    void externalCall() {
        externalService.external();
    }
}
