package hello.jdbc.repository;

import hello.jdbc.domain.Member;

public interface MemberRepository {
    Member save(Member member);
    Member findMember(String memberId);
    void updateMember(String memberId, int money);
    void deleteMember(String memberId);
}
