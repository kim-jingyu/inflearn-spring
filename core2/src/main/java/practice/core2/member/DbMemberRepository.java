package practice.core2.member;

import org.springframework.stereotype.Repository;

@Repository
public class DbMemberRepository implements MemberRepository{
    @Override
    public void saveMember(Member member) {

    }

    @Override
    public Member findById(Long memberId) {
        return null;
    }
}
