package hello.jdbc2.repository;

import hello.jdbc2.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 * DataSourceUtils.getConnection()
 * DataSourceUtils.releaseConnection()
 */
@Slf4j
public class MemberRepositoryV3 {
    private final DataSource dataSource;

    public MemberRepositoryV3(DataSource dataSource) {
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
        // 트랜잭션 동기화를 사용하기 위해서 DataSourceUtils를 사용해 Connection을 get 해준다.
        Connection connection = DataSourceUtils.getConnection(dataSource);
        log.info("getConnection() = {}, class = {}", connection, connection.getClass());
        return connection;
    }

    private void close(Connection connection, PreparedStatement ps, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(ps);
        // 트랜잭션 동기화를 사용하기 위해서 DataSourceUtils를 사용해 Connection을 release 해준다.
        DataSourceUtils.releaseConnection(connection, dataSource);
    }
}
