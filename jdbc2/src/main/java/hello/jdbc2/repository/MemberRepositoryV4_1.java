package hello.jdbc2.repository;

import hello.jdbc2.domain.Member;
import hello.jdbc2.repository.exception.MyDbException;
import lombok.RequiredArgsConstructor;
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
 * 예외 누수 문제 해결
 * 1. 체크 예외를 런타임 예외로 변경
 * 2. MemberRepository 인터페이스 사용
 * 3. throws SQLException 제거
 */
@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV4_1 implements MemberRepository{
    private final DataSource dataSource;

    @Override
    public Member save(Member member) {
        String sql = "insert into member(id, money) values(?, ?)";

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
            throw new MyDbException(e);
        } finally {
            close(connection, ps, null);
        }
    }

    @Override
    public Member findById(String id) {
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
                throw new NoSuchElementException("member not found id = " + id);
            }
        } catch (SQLException e) {
            throw new MyDbException(e);
        } finally {
            close(connection, ps, rs);
        }
    }

    @Override
    public void update(String id, int money) {
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
            throw new MyDbException(e);
        } finally {
            close(connection, ps, null);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "delete from member where id = ?";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MyDbException(e);
        } finally {
            close(connection, ps, null);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection connection, PreparedStatement ps, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(ps);
        DataSourceUtils.releaseConnection(connection, dataSource);
    }
}
