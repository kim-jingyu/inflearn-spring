package hello.jdbc.service;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberServiceV2Test {

    private MemberRepositoryV2 memberRepository;
    private MemberServiceV2 memberService;

    @BeforeEach
    void before() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPOOL!!");

        memberRepository = new MemberRepositoryV2(dataSource);
        memberService = new MemberServiceV2(dataSource, memberRepository);
    }

    @AfterEach
    void after() throws SQLException {
        memberRepository.delete("memberA");
        memberRepository.delete("memberB");
        memberRepository.delete("ex");
    }

    @Test
    @DisplayName("정상 이체 테스트")
    void accountTransfer() throws SQLException, InterruptedException {
        Member memberA = new Member("memberA", 10000);
        Member memberB = new Member("memberB", 20000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        memberService.accountTransfer(memberA.getMemberID(), memberB.getMemberID(), 5000);

        Member foundMemberA = memberRepository.findMember(memberA.getMemberID());
        Member foundMemberB = memberRepository.findMember(memberB.getMemberID());

        assertThat(foundMemberA.getMoney()).isEqualTo(5000);
        assertThat(foundMemberB.getMoney()).isEqualTo(25000);

        Thread.sleep(1000);
    }

    @Test
    @DisplayName("이체중 예외 발생 테스트")
    void accountTransferEx() throws SQLException, InterruptedException {
        Member memberA = new Member("memberA", 10000);
        Member memberEx = new Member("ex", 20000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        /**
         * memberEx 회원의 ID 가 ex 이므로 중간에 예외가 발생한다.
         * 예외가 발생했으므로 트랜잭션을 rollback 한다.
         */
        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberID(), memberEx.getMemberID(), 8000))
                .isInstanceOf(IllegalStateException.class);

        Member foundMemberA = memberRepository.findMember(memberA.getMemberID());
        Member foundMemberEx = memberRepository.findMember(memberEx.getMemberID());

        /**
         * 계좌이체는 실패했다.
         * rollback 을 수행해서 memberA 의 돈이 기존 10000원 으로 복구되었다.
         */
        assertThat(foundMemberA.getMoney()).isEqualTo(10000);
        assertThat(foundMemberEx.getMoney()).isEqualTo(20000);

        Thread.sleep(1000);
    }
}