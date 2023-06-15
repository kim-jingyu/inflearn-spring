package hello.jdbc.service;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import org.assertj.core.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.*;

/**
 * 트랜잭션 매니저 사용 테스트
 */
class MemberServiceV3_1Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String EX = "ex";
    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_1 memberService;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberRepository = new MemberRepositoryV3(dataSource);
        memberService = new MemberServiceV3_1(transactionManager, memberRepository);
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
        Member memberA = new Member(MEMBER_A, 7000);
        Member memberB = new Member(MEMBER_B, 5400);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        memberService.accountTransfer(memberA.getMemberID(), memberB.getMemberID(), 2000);

        Member foundMemberA = memberRepository.findById(memberA.getMemberID());
        Member foundMemberB = memberRepository.findById(memberB.getMemberID());
        assertThat(foundMemberA.getMoney()).isEqualTo(5000);
        assertThat(foundMemberB.getMoney()).isEqualTo(7400);
    }

    @Test
    @DisplayName("이체중 예외 발생 테스트")
    void accountTransferEx() throws SQLException {
        Member memberA = new Member(MEMBER_A, 7000);
        Member memberEX = new Member(EX, 5400);
        memberRepository.save(memberA);
        memberRepository.save(memberEX);

        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberID(), memberEX.getMemberID(), 2000))
                .isInstanceOf(IllegalArgumentException.class);

        Member foundMemberA = memberRepository.findById(memberA.getMemberID());
        assertThat(foundMemberA.getMoney()).isEqualTo(7000);
        Member foundMemberB = memberRepository.findById(memberEX.getMemberID());
        assertThat(foundMemberB.getMoney()).isEqualTo(5400);
    }
}