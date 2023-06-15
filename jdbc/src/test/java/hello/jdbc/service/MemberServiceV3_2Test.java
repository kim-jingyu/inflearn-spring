package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;

/**
 * 트랜잭션 템플릿
 */
class MemberServiceV3_2Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String EX = "ex";
    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_2 memberService;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberRepository = new MemberRepositoryV3(dataSource);
        memberService = new MemberServiceV3_2(transactionManager, memberRepository);
    }

    @AfterEach
    void after() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(EX);
    }

    @Test
    @DisplayName("정상 이체 테스트")
    void accountTransfer() throws SQLException {
        Member memberA = new Member(MEMBER_A, 7500);
        Member memberB = new Member(MEMBER_B, 4300);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        memberService.accountTransfer(memberA.getMemberID(), memberB.getMemberID(), 700);

        Member foundMemberA = memberRepository.findById(memberA.getMemberID());
        Member foundMemberB = memberRepository.findById(memberB.getMemberID());
        assertThat(foundMemberA.getMoney()).isEqualTo(6800);
        assertThat(foundMemberB.getMoney()).isEqualTo(5000);
    }

    @Test
    @DisplayName("이체중 오류 테스트")
    void accountTransferEx() throws SQLException {
        Member memberA = new Member(MEMBER_A, 7500);
        Member memberEx = new Member(EX, 4300);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberID(), memberEx.getMemberID(), 700))
                .isInstanceOf(IllegalArgumentException.class);

        Member foundMemberA = memberRepository.findById(memberA.getMemberID());
        Member foundMemberEx = memberRepository.findById(memberEx.getMemberID());
        assertThat(foundMemberA.getMoney()).isEqualTo(7500);
        assertThat(foundMemberEx.getMoney()).isEqualTo(4300);
    }
}