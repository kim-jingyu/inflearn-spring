package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.*;

/**
 * 트랜잭션 적용 확인
 * 트랜잭션 관련 코드가 눈에 보이지 않고, AOP 를 기반으로 동작하기 때문에 실제 트랜잭션이 적용되고 있는지 아닌지를 확인하기 어렵다.
 * 스프링 트랜잭션이 실제 적용되고 있는지 확인
 */
@Slf4j
@SpringBootTest
public class TxApplyBasicTest {

    @Slf4j
    static class BasicService {

        @Transactional
        public void tx() {
            log.info("트랜잭션 적용 메서드 호출");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("트랜잭션 활성화 여부 = {}",txActive);
        }

        public void nonTx() {
            log.info("트랜잭션 미적용 메서드 호출");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("트랜잭션 활성화 여부 = {}",txActive);
        }
    }

    @TestConfiguration
    static class TxApplyBasicConfig {
        @Bean
        BasicService basicService() {
            return new BasicService();
        }
    }

    @Autowired
    BasicService basicService;

    /**
     * 프록시 적용 여부 확인
     */
    @Test
    void proxyCheck() {
        log.info("basicService.getClass() 결과 = {}", basicService.getClass());
        assertThat(AopUtils.isAopProxy(basicService)).isTrue();
    }

    @Test
    void txTest() {
        basicService.tx();
        basicService.nonTx();
    }

}
