package hello.jdbc2.repository;

import hello.jdbc2.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc2.connection.DBConnectionUtil.getConnection;

/**
 * JDBC - DataSource 사용, JdbcUtils 사용
 * JdbcUtils = 스프링에서 제공하는 JDBC를 편리하게 다룰 수 있는 편의 메서드, Connection을 좀 더 편리하게 닫을 수 있다.
 */
@Slf4j
public class MemberRepositoryV1 {
    private final DataSource dataSource;

    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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

    public void update(String id, int money) throws SQLException {
        String sql = "update member set money = ? where id = ?";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, money);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("회원 수정 중 에러 발생!", e);
            throw e;
        } finally {
            close(connection, ps, null);
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "delete from member where id = ?";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("회원 삭제 중 에러 발생!", e);
            throw e;
        } finally {
            close(connection, ps, null);
        }
    }

    private void close(Connection connection, PreparedStatement ps, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(ps);
        JdbcUtils.closeConnection(connection);
    }

}
