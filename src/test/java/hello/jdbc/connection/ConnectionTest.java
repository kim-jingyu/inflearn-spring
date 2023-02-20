package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    /**
     * DriverManager 를 통해 Getting Connection
     */
    @Test
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("con1 정보 = {}", con1);
        log.info("con2 정보 = {}", con2);
    }

    /**
     * DriverManagerDataSource - 항상 새로운 Connection 획득
     */
    @Test
    void driverManagerDataSource() throws SQLException {
        // 설정
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // 사용
        // Repository 는 DataSource 만 의존한다.
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();

        log.info("con1 정보 = {}", con1);
        log.info("con2 정보 = {}", con2);
    }

    /**
     * HikariCP Connection Pool 사용
     * Connection Pooling : HikariProxyConnection(Proxy) -> JdbcConnection(Target)
     */
    @Test
    void hikariDataSource() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);

        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();

        /**
         * Connection Pool 에서 Connection 을 생성하는 작업은 애플리케이션 실행 속도에 영향을 주지 않기 위해 별도의 쓰레드에서 작동한다.
         * 별도의 Thread 에서 동작하기 때문에 테스트가 먼저 종료되어 버린다.
         * 대기 시간을 주어야 Thread Pool 에 Connection 이 생성되는 로그를 확인할 수 있다.
         */
        Thread.sleep(1000); // Connection Pool 에서 Connection 생성 시간 대기
    }
}
