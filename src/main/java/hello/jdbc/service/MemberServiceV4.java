package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * 예외 누수 문제 해결 - SQLException 제거
 * MemberRepository Interface 의존
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV4 {

    // MemberRepository Interface 에 의존하도록 코드 변경
//    private final MemberRepositoryV3 memberRepository;
    private final MemberRepository memberRepository;

    /**
     * 스프링이 제공하는 트랜잭션 AOP 를 적용하기 위해 @Transactional 애노테이션을 적용했다.
     */
    @Transactional
    public void accountTransfer(String fromId, String toId, int money){
        bizLogic(fromId, toId, money);
    }

    private void bizLogic(String fromId, String toId, int money){
        Member fromMember = memberRepository.findMember(fromId);
        Member toMember = memberRepository.findMember(toId);

        memberRepository.updateMember(fromMember.getMemberID(), fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.updateMember(toMember.getMemberID(), toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberID().equals("ex")) {
            throw new IllegalArgumentException("잘못된 사용자에게 이체할 수 없습니다.");
        }
    }
}
