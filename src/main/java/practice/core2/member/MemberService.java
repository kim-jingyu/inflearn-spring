package practice.core2.member;

public interface MemberService {
    void signup(Member member);
    Member findMember(Long memberId);
}
