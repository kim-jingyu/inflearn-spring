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

/**
 * JDBC - ConnectionParam
 */
@Slf4j
public class MemberRepositoryV2 {
    private final DataSource dataSource;

    public MemberRepositoryV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(id, money) values (?,?)";

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
            log.error("db error", e);
            throw e;
        } finally {
            close(connection, ps, null);
        }
    }

    public Member findById(String id) throws SQLException {
        String sql = "select * from member where id = ?";

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getString("id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found. " + id);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(connection, ps, rs);
        }
    }

    public Member findById(Connection connection ,String id) throws SQLException {
        String sql = "select * from member where id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getString("id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found. " + id);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
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
            log.error("db error", e);
            throw e;
        } finally {
            close(connection, ps, null);
        }
    }

    public void update(Connection connection, String id, int money) throws SQLException {
        String sql = "update member set money = ? where id = ?";

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, money);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            JdbcUtils.closeStatement(ps);
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
            log.error("db error", e);
            throw e;
        } finally {
            close(connection, ps, null);
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void close(Connection connection, PreparedStatement ps, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(ps);
        JdbcUtils.closeConnection(connection);
    }
}
