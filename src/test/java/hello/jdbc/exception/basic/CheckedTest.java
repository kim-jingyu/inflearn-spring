package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class CheckedTest {

    @Test
    @DisplayName("체크 예외 처리 테스트")
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    @DisplayName("체크 예외 던지기 테스트")
    void checked_throws() {
        Service service = new Service();
        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }

    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    static class Service {
        private Repository repository = new Repository();

        public void callCatch() {
            try {
                repository.checkedExceptionCall();
            } catch (MyCheckedException e) {
                // 예외 처리 로직
                log.info("Checked 예외 처리 ( try - catch ), message = {}", e.getMessage(), e);
            }
        }

        public void callThrow() throws MyCheckedException {
            repository.checkedExceptionCall();
        }

        static class Repository {
            public void checkedExceptionCall() throws MyCheckedException {
                throw new MyCheckedException("Checked Exception 발생!!");
            }
        }
    }
}
