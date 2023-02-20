package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

/**
 * JDBC 사용해서 DB에 연결
 */
@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection() {
        try {
            // DB에 연결하려면 JDBC 가 제공하는 DriverManager.getConnection 을 사용하면 된다.
            // 라이브러리에 있는 DB 드라이버를 찾아서 실제 DB와 connection 을 맺고
            // 해당 드라이버가 제공하는 connection 을 반환해준다.
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // class org.h2.jdbc.JdbcConnection : H2 데이터베이스 드라이버가 제공하는 H2 전용 Connection
            log.info("connection 정보 = {}, 클래스 = {}", connection, connection.getClass());
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
