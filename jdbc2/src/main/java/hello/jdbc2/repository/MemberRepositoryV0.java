package hello.jdbc2.repository;

import hello.jdbc2.connection.DBConnectionUtil;
import hello.jdbc2.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member values(?, ?)";

        // connection
        Connection conn = null;
        // sql 전달
        PreparedStatement psmt = null;

        try {
            conn = DBConnectionUtil.getConnection();
            psmt = conn.prepareStatement(sql);
            // sql 전달시, 파라미터 세팅
            psmt.setString(1, member.getMemberId());
            psmt.setInt(2, member.getMoney());
            // statement를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에 전달
            psmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(conn, psmt, null);
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("rs error", e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("smtm error", e);
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.info("conn error", e);
            }
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtil.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, memberId);
            rs = psmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(conn, psmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection conn = null;
        PreparedStatement psmt = null;

        try {
            conn = DBConnectionUtil.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, money);
            psmt.setString(2, memberId);
            int resultSize = psmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(conn, psmt, null);
        }
    }
}
