package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class MemberRepositoryV4_2 implements MemberRepository {

    private final DataSource dataSource;
    private final SQLExceptionTranslator exceptionTranslator;

    public MemberRepositoryV4_2(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member values(?,?)";

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
            throw exceptionTranslator.translate("save task", sql, e);
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
                throw new NoSuchElementException("해당 회원 ID 로 조회된 회원이 없습니다. 회원 ID = " + memberId);
            }
        } catch (SQLException e) {
            throw exceptionTranslator.translate("findMember tast", sql, e);
        }finally {
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
            throw exceptionTranslator.translate("updateMember task", sql, e);
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
            throw exceptionTranslator.translate("deleteMember task", sql, e);
        }finally {
            close(con,psmt,null);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection con, PreparedStatement psmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(psmt);
        DataSourceUtils.releaseConnection(con, dataSource);
    }
}
