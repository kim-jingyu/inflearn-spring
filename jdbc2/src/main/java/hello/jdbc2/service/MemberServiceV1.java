package hello.jdbc2.service;

import hello.jdbc2.domain.Member;
import hello.jdbc2.repository.MemberRepositoryV1;

import java.sql.SQLException;

public class MemberServiceV1 {
    private final MemberRepositoryV1 memberRepository;

    public MemberServiceV1(MemberRepositoryV1 memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
