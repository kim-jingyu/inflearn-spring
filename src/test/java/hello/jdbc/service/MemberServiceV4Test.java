package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV3;
import hello.jdbc.repository.MemberRepositoryV4_1;
import hello.jdbc.repository.MemberRepositoryV4_2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 예외 누수 문제 해결 - SQLException 제거
 * MemberRepository Interface 의존
 */
@Slf4j
@SpringBootTest
class MemberServiceV4Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String EX = "ex";

    // MemberRepository Interface 의존
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberServiceV4 memberService;

    @AfterEach
    void after() throws SQLException {
        memberRepository.deleteMember(MEMBER_A);
        memberRepository.deleteMember(MEMBER_B);
        memberRepository.deleteMember(EX);
    }

    @TestConfiguration
    static class TestConfig {

        private final DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        MemberRepository memberRepository() {
//            return new MemberRepositoryV4_1(dataSource); // 런타임 예외로 변환 ( 단순 예외 변환 )
            return new MemberRepositoryV4_2(dataSource); // 스프링 예외 변환
        }

        @Bean
        MemberServiceV4 memberService() {
            return new MemberServiceV4(memberRepository());
        }
    }

    @Test
    void AopCheck() {
        log.info("memberService.getClass() = {}", memberService.getClass());
        log.info("memberRepository.getClass = {}", memberRepository.getClass());
        assertThat(AopUtils.isAopProxy(memberService)).isTrue();
        assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
    }

    @Test
    @DisplayName("정상 이체 테스트")
    void accountTransfer() {
        Member memberA = new Member(MEMBER_A, 8300);
        Member memberB = new Member(MEMBER_B, 3200);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        memberService.accountTransfer(memberA.getMemberID(), memberB.getMemberID(), 2000);

        Member foundMemberA = memberRepository.findMember(memberA.getMemberID());
        Member foundMemberB = memberRepository.findMember(memberB.getMemberID());
        assertThat(foundMemberA.getMoney()).isEqualTo(6300);
        assertThat(foundMemberB.getMoney()).isEqualTo(5200);
    }

    @Test
    @DisplayName("이체중 오류 테스트")
    void accountTransferEx() {
        Member memberA = new Member(MEMBER_A, 8300);
        Member memberEx = new Member(EX, 3200);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberID(), memberEx.getMemberID(), 2000))
                .isInstanceOf(IllegalArgumentException.class);

        Member foundMemberA = memberRepository.findMember(memberA.getMemberID());
        Member foundMemberEx = memberRepository.findMember(memberEx.getMemberID());
        assertThat(foundMemberA.getMoney()).isEqualTo(8300);
        assertThat(foundMemberEx.getMoney()).isEqualTo(3200);
    }
}