package hello.springtx.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

/**
 * Unchecked Exception -> rollback
 */
@SpringBootTest
@Slf4j
public class RollbackTest {
    static class MyException extends Exception {
    }

    static class ExService {
        @Transactional
        public void unchecked() {
            log.info("RuntimeException 발생!");
            throw new RuntimeException();
        }

        @Transactional
        public void checked() throws MyException {
            log.info("MyException 발생!");
            throw new MyException();
        }

        @Transactional(rollbackFor = MyException.class)
        public void rollbackFor() throws MyException {
            log.info("MyException -> rollbackFor 적용!");
            throw new MyException();
        }
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        ExService exService() {
            return new ExService();
        }
    }

    private final ExService exService;

    @Autowired
    public RollbackTest(ExService exService) {
        this.exService = exService;
    }

    @Test
    void 런타임_예외_톄스트() {
        assertThatThrownBy(() -> exService.unchecked())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void 체크_예외_테스트() {
        assertThatThrownBy(() -> exService.checked())
                .isInstanceOf(MyException.class);
    }

    @Test
    void rollbackFor() {
        assertThatThrownBy(() -> exService.rollbackFor())
                .isInstanceOf(MyException.class);
    }
}
