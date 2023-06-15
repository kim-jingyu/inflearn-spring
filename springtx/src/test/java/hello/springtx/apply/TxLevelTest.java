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

/**
 * 트랜잭션 적용 위치
 */
@SpringBootTest
@Slf4j
public class TxLevelTest {
    @Transactional(readOnly = true)
    static class LevelService {

        @Transactional(readOnly = false)
        public void write() {
            log.info("call write");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive(); // 트랜잭션 적용 여부 확인
            log.info("write 메서드 트랜잭션 적용 여부 = {}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly(); // 트랜잭션 readOnly 적용 여부 확인
            log.info("write 메서드 트랜잭션 readOnly 적용 여부 확인 = {}", readOnly);
        }

        public void read() {
            log.info("call read");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("read 메서드 트랜잭션 적용 여부 = {}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("read 메서드 트랜잭션 readOnly 적용 여부 확인 = {}", readOnly);
        }
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        LevelService levelService() {
            return new LevelService();
        }
    }

    @Autowired
    LevelService levelService;

    @Test
    void aopCheck() {
        log.info("levelService.getClass() = {}",levelService.getClass());
        boolean aopProxy = AopUtils.isAopProxy(levelService);
        log.info("AopUtils.isAopProxy(levelService) = {}", aopProxy);
        Assertions.assertThat(aopProxy).isTrue();
    }

    @Test
    void txReadOnly() {
        levelService.write();
        levelService.read();
    }
}
