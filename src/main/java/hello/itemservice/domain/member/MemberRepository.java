package hello.itemservice.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Member saveMember(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        log.info("save memberId = {}, memberName = {}", member.getId(), member.getName());
        return member;
    }

    public Member findMemberByID(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAllMember().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAllMember() {
        return new ArrayList<>(store.values());
    }

    // 테스트용 메서드
    public void clearStore() {
        store.clear();
    }
}
