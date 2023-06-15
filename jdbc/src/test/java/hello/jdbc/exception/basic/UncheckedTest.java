package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unchecked Exception 은
 * 예외를 잡거나, 던지지 않아도 된다.
 * 예외를 잡지 않으면 자동으로 밖으로 던진다.
 */
@Slf4j
public class UncheckedTest {

    /**
     * RuntimeException 을 상속받은 예외는 언체크 예외가 된다.
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    static class Repository {
        public void call() {
            throw new MyUncheckedException("Unchecked Exception!!");
        }
    }

    static class Service {
        Repository repository = new Repository();

        /**
         * 필요하면 Unchecked Exception 잡아서 처리하기
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외 처리 로직 message = {}", e.getMessage(), e);
            }
        }

        /**
         * 예외를 잡지 않아도 던질 필요가 없다.
         */
        public void callThrow() {
            repository.call();
        }
    }

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }
}