package practice.core2.member;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new ConcurrentHashMap<>();

    @Override
    public void saveMember(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
