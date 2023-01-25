package practice.core2.member;

public interface MemberRepository {
    void saveMember(Member member);

    Member findById(Long memberId);
}
