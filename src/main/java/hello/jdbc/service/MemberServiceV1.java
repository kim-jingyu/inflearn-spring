package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

/**
 * 트랜잭션 없이 단순 계좌이체 비즈니스 로직
 */
@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;

    /**
     * fromId 회원 -> toId 회원에게 money 만큼 돈을 계좌이체 ( update sql )
     * 예외 상황을 테스트해보기 위해 toId가 ex 인 경우 예외를 발생한다.
     */
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findMember(fromId);
        Member toMember = memberRepository.findMember(toId);

        memberRepository.updateMember(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.updateMember(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberID().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
