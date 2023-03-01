package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
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
 * 체크 예외를 런타임 예외로 변경
 */
@Slf4j
public class MemberRepositoryV4_1 implements MemberRepository{

    private final DataSource dataSource;

    public MemberRepositoryV4_1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member values(?, ?)";

        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con = getConnection();

            psmt = con.prepareStatement(sql);
            psmt.setString(1, member.getMemberID());
            psmt.setInt(2, member.getMoney());

            psmt.executeUpdate();

            return member;
        } catch (SQLException e) {
            throw new MyDbException(e);
        }finally {
            close(con, psmt, null);
        }
    }

    @Override
    public Member findMember(String memberId) {
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
                throw new NoSuchElementException("해당 정보로 조회된 회원이 없습니다. 회원 ID " + memberId);
            }
        } catch (SQLException e) {
            throw new MyDbException(e);
        } finally {
            close(con, psmt, rs);
        }
    }

    @Override
    public void updateMember(String memberId, int money) {
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
            throw new MyDbException(e);
        }finally {
            close(con, psmt, null);
        }
    }

    @Override
    public void deleteMember(String memberId) {
        String sql = "delete from member where member_id = ?";

        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con = getConnection();

            psmt = con.prepareStatement(sql);
            psmt.setString(1, memberId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new MyDbException(e);
        }finally {
            close(con, psmt, null);
        }
    }

    private Connection getConnection() {
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("connection = {}, class = {}", con, con.getClass());
        return con;
    }

    private void close(Connection con, PreparedStatement psmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(psmt);
        DataSourceUtils.releaseConnection(con, dataSource);
    }
}
