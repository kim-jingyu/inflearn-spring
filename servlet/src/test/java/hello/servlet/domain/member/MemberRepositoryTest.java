package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        // 이름=hello, 나이=20
        Member member = new Member("hello", 20);

        //when -> 실행했을때
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> members = memberRepository.findAll();

        //then
        for (Member member : members) {
            System.out.println("member.getId() = " + member.getId());
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getAge() = " + member.getAge());
        }

        assertThat(members.size()).isEqualTo(2);
        assertThat(members).contains(member1, member2);
    }
}