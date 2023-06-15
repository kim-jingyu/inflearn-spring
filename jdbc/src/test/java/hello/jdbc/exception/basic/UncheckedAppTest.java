package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

/**
 * SQLException 대신에 RuntimeSQLException 으로 변환
 * ConnectException 대신에 RuntimeConnectException 으로 변환
 */
@Slf4j
public class UncheckedAppTest {

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException() {
        }

        public RuntimeSQLException(String message) {
            super(message);
        }

        public RuntimeSQLException(String message, Throwable cause) {
            super(message, cause);
        }

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }

    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectException("RuntimeConnectException 연결 실패!!");
        }
    }

    static class Repository {

        public void call() {
            try {
                runSQLEx();
            } catch (SQLException e) { // 체크 예외에서 런타임 예외로 전환해서 예외를 던진다.
                // 이때 기존 예외를 포함해주어야 예외 출력시 스택 트레이스에서 기존 예외도 함께 확인할 수 있다.
                throw new RuntimeSQLException(e);
            }
        }

        private void runSQLEx() throws SQLException {
            throw new SQLException("SQLException");
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    @Test
    void unchecked() {
        Controller controller = new Controller();

        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    @Test
    void printEx() {
        Controller controller = new Controller();

        try {
            controller.request();
        } catch (Exception e) {
            log.info("예외 캐치!!", e);
        }
    }

}
