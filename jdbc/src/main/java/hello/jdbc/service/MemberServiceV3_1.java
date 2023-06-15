package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

/**
 * 트랜잭션 매니저 - Service 에 적용
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

    // 트랜잭션 매니저를 주입받는다.
    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    /**
     * 계좌 이체 로직
     */
    public void accountTransfer(String fromId, String toId, int money) {
        // 트랜잭션을 시작한다.
        // Definition -> 트랜잭션과 관련된 옵션을 지정할 수 있다.
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            // 비즈니스 로직
            bizLogic(fromId, toId, money);

            // 성공시 commit
            transactionManager.commit(status);
        } catch (Exception e) {
            // 실패시 rollback
            transactionManager.rollback(status);
            throw new IllegalArgumentException(e);
        }
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberID().equals("ex")) {
            throw new IllegalArgumentException("잘못된 사용자에게 이체할 수 없습니다.");
        }
    }
}
