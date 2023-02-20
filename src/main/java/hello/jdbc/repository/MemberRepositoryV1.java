package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * JDBC - DataSource 사용, JdbcUtils 사용
 */
@Slf4j
public class MemberRepositoryV1 {

    /**
     * DataSource 의존관계 주입
     * 표준 인터페이스 이기 때문에, 커넥션 풀 구현체가 변경되어도 해당 코드를 변경하지 않아도 된다.
     */
    private final DataSource dataSource;

    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * JDBC 를 사용해서 Member 객체를 DB 에 저장한다. ( 등록 )
     */
    public Member save(Member member) throws SQLException {
        String sql = "insert into MEMBER(member_id, money) values (?, ?)";

        Connection con = null;
        /**
         * PreparedStatement 는 Statement 의 자식 타입인데, ? 를 통한 파라미터 바인딩을 가능하게 해준다.
         * SQL Injection 공격을 예방하려면 PreparedStatement 를 통한 파라미터 바인딩 방식을 사용해야 한다.
         */
        PreparedStatement psmt = null;

        try {
            // DB Connection 획득
            con = getConnection();

            // DB 에 전달할 SQL 과 파라미터로 전달할 데이터들을 준비
            psmt = con.prepareStatement(sql);
            psmt.setString(1, member.getMemberID());
            psmt.setInt(2, member.getMoney());

            // Statement 를 통해 준비된 SQL 을 커넥션을 통해 실제 DB 에 전달한다.
            // 반환 값은 영향받은 DB row 수
            psmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("DB error", e);
            throw e;
        }finally {
            /**
             * 쿼리를 실행하고 나면 리소스를 정리해야 한다. (역순으로)
             * 만약 이 부분을 놓치게 되면 커넥션이 끊어지지 않고 계속 유지되는 문제가 발생할 수 있다.
             * 리소스 누수
             * 결과적으로 커넥션 부족으로 장애 발생 가능
             */
            close(con, psmt, null);
        }
    }

    /**
     * 회원 조회
     */
    public Member findMember(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            psmt = con.prepareStatement(sql);
            psmt.setString(1, memberId);

            /**
             * 데이터를 조회할 때는 executeQuery() 를 사용한다.
             * 결과는 ResultSet 에 담아서 반환한다.
             */
            rs = psmt.executeQuery();

            /**
             * rs.next() 를 호출하면 커서가 다음으로 이동한다.
             * 결과가 true 면 커서의 이동 결과 데이터가 있다는 뜻
             * 결과가 false 면 더이상 커서가 가리키는 데이터가 없다는 뜻
             */
            if (rs.next()) {
                Member member = new Member();

                // 현재 커서가 가리키고 있는 위치의 member_id 데이터를 String 타입으로 반환한다.
                member.setMemberID(rs.getString("member_id"));

                // 현재 커서가 가리키고 있는 위치의 money 데이터를 int 타입으로 반환한다.
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("회원 아이디가 " + memberId + "인 회원은 없습니다.");
            }

        } catch (SQLException e) {
            log.error("DB error", e);
            throw e;
        } finally {
            close(con, psmt, rs);
        }
    }

    /**
     * 회원 수정
     */
    public void updateMember(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con = getConnection();

            psmt = con.prepareStatement(sql);
            psmt.setInt(1, money);
            psmt.setString(2, memberId);

            int resultSize = psmt.executeUpdate();
            log.info("resultSize = {}", resultSize);

        } catch (SQLException e) {
            log.error("DB error", e);
            throw e;
        } finally {
            close(con, psmt, null);
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
            log.error("DB error", e);
            throw e;
        } finally {
            close(con, psmt, null);
        }
    }

    /**
     * JdbcUtils 를 사용하면 커넥션을 좀 더 편리하게 닫을 수 있다.
     */
    private void close(Connection con, PreparedStatement psmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(psmt);
        JdbcUtils.closeConnection(con);
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("Connection 정보 = {}, 클래스 = {}", con, con.getClass());
        return con;
    }
}
