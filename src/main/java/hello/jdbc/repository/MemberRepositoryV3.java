package hello.jdbc.repository;

import hello.jdbc.domain.Member;
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
 * 트랜잭션 문제 해결 - 트랜잭션 매니저
 * DataSourceUtils.getConnection()
 * DataSourceUtils.releaseConnection()
 */
@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV3 implements MemberRepositoryEx{
    private final DataSource dataSource;

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement psmt = null;

        try {
            // DB Connection 획득
            con = getConnection();

            // DB 에 전달할 sql 과 파라미터로 전달할 데이터들을 준비
            psmt = con.prepareStatement(sql);
            psmt.setString(1, member.getMemberID());
            psmt.setInt(2, member.getMoney());

            // Statement 를 통해 준비된 sql 을 connection 을 통해서 실제 DB 에 전달한다.
            psmt.executeUpdate();

            return member;
        } catch (SQLException e) {
            log.error("DB error!!", e);
            throw e;
        } finally {
            close(con, psmt,null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            psmt = con.prepareStatement(sql);
            psmt.setString(1, memberId);

            rs = psmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberID(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found!! MemberId = " + memberId);
            }
        } catch (SQLException e) {
            log.error("DB error!!", e);
            throw e;
        } finally {
            close(con, psmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con = getConnection();

            psmt = con.prepareStatement(sql);
            psmt.setInt(1, money);
            psmt.setString(2, memberId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("DB error!", e);
            throw e;
        } finally {
            close(con,psmt,null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con = getConnection();

            psmt = con.prepareStatement(sql);
            psmt.setString(1, memberId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("DB error!!", e);
            throw e;
        } finally {
            close(con, psmt, null);
        }
    }

    private void close(Connection con, PreparedStatement psmt, ResultSet rs) throws SQLException {
        // JdbcUtils 를 사용하면 커넥션을 좀 더 편리하게 닫을 수 있다.
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(psmt);
        // DataSourceUtils.releaseConnection()
        // 트랜잭션을 사용하기 위해 동기화된 커넥션은 닫지 않고 그대로 유지해준다.
        DataSourceUtils.releaseConnection(con, dataSource);
    }

    private Connection getConnection() {
        // DataSourceUtils.getConnection()
        // 트랜잭션 동기화 매니저가 관리하는 커넥션이 있으면 해당 커넥션을 반환한다.
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("DataSourceUtils.getConnection = {}, class = {} ", con, con.getClass());
        return con;
    }
}
