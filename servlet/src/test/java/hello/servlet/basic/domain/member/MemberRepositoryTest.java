package hello.servlet.basic.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void init() {
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("회원 저장 테스트")
    void save() {
        Member member = new Member("kim", 10);

        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember.getName()).isEqualTo("kim");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    @DisplayName("회원 목록 조회 테스트")
    void findAll() {
        Member kim = new Member("kim", 10);
        Member lee = new Member("lee", 20);

        memberRepository.save(kim);
        memberRepository.save(lee);

        List<Member> findMembers = memberRepository.findAll();
        assertThat(findMembers).hasSize(2);
        assertThat(findMembers).extracting("name").containsExactlyInAnyOrder("kim", "lee");
        assertThat(findMembers).extracting("age").containsExactlyInAnyOrder(10, 20);
    }
}