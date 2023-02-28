package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

/**
 * 체크 예외의 문제점
 * 1. 복구 불가능한 예외
 * 2. 의존 관계에 대한 문제
 */
@Slf4j
public class CheckedAppTest {

    static class NetworkClient {
        public void call() throws ConnectException {
            throw new ConnectException("ConnectException 연결 실패!!");
        }
    }

    static class Repository {
        public void call() throws SQLException {
            throw new SQLException("SQLException DB 에러!!");
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() throws ConnectException, SQLException {
            networkClient.call();
            repository.call();
        }
    }


    static class Controller {
        Service service = new Service();

        public void request() throws SQLException, ConnectException {
            service.logic();
        }
    }

    @Test
    void checked() {
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }
}
