package hello.jdbc2.repository;

import hello.jdbc2.connection.DBConnectionUtil;
import hello.jdbc2.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static hello.jdbc2.connection.DBConnectionUtil.*;

/**
 * JDBC - DriverManager
 */
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(id, money) values (?, ?)";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, member.getId());
            ps.setInt(2, member.getMoney());
            ps.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("회원 저장 중 에러 발생!", e);
            throw e;
        } finally {
            close(connection, ps, null);
        }
    }

    private void close(Connection connection, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("Result Set 닫는 중 에러 발생!", e);
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                log.error("Prepared Statement 닫는 중 에러 발생!", e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Connection 닫는 중 에러 발생!", e);
            }
        }
    }

}
