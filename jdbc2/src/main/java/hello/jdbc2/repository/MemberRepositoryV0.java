package hello.jdbc2.repository;

import hello.jdbc2.connection.DBConnectionUtil;
import hello.jdbc2.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

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

    public Member findById(String id) throws SQLException {
        String sql = "select * from member where id = ?";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getString("id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException(id + "회원 검색 결과 없음!");
            }

        } catch (SQLException e) {
            log.error("회원 조회 중 에러 발생!", e);
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
