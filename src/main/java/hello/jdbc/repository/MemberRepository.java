package hello.jdbc.repository;

import hello.jdbc.domain.Member;

public interface MemberRepository {
    public Member save(Member member);
    public Member findMember(String memberId);

    public void updateMember(String memberId, int money);

    public void deleteMember(String memberId);
}
