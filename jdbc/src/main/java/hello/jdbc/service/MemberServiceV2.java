package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 연동 로직
 * 파라미터 연동, Pool 을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();

        try {
            // 트랜잭션 시작
            con.setAutoCommit(false);

            // 비즈니스 로직
            businessLogic(fromId, toId, money, con);

            // 성공시 커밋
            con.commit();
        } catch (Exception e) {
            // 실패시 롤백
            con.rollback();

            throw new IllegalStateException(e);
        }finally {
            release(con);
        }

    }

    private void release(Connection con) {
        if (con != null) {
            try {
                // 다음 사용할 커넥션 풀 고려해서 기본 값인 autocommit 모드로 세팅
                con.setAutoCommit(true);

                // 커넥션 풀 반환
                con.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
    }

    private void businessLogic(String fromId, String toId, int money, Connection con) throws SQLException {
        Member fromMember = memberRepository.findMember(con, fromId);
        Member toMember = memberRepository.findMember(con, toId);

        memberRepository.updateMember(con, fromId, fromMember.getMoney() - money);
        // 검증
        validation(toMember);
        memberRepository.updateMember(con, toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberID().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
