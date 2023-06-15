package hello.jdbc.service;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - 스프링 부트가 DataSource, transactionManager 자동 등록
 */
@Slf4j
@SpringBootTest
class MemberServiceV3_4Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String EX = "ex";
    @Autowired
    MemberRepositoryV3 memberRepository;
    @Autowired
    MemberServiceV3_3 memberService;

    @AfterEach
    void after() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(EX);
    }

    @TestConfiguration
    static class TestConfig {

        private final DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        //        @Bean
//        DataSource dataSource() {
//            HikariDataSource dataSource = new HikariDataSource();
//            dataSource.setJdbcUrl(URL);
//            dataSource.setUsername(USERNAME);
//            dataSource.setPassword(PASSWORD);
//            return dataSource;
//        }

//        @Bean
//        PlatformTransactionManager transactionManager() {
//            return new DataSourceTransactionManager(dataSource());
//        }

        @Bean
        MemberRepositoryV3 memberRepository() {
            return new MemberRepositoryV3(dataSource);
        }

        @Bean
        MemberServiceV3_3 memberService() {
            return new MemberServiceV3_3(memberRepository());
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
    void accountTransfer() throws SQLException {
        Member memberA = new Member(MEMBER_A, 8300);
        Member memberB = new Member(MEMBER_B, 3200);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        memberService.accountTransfer(memberA.getMemberID(), memberB.getMemberID(), 2000);

        Member foundMemberA = memberRepository.findById(memberA.getMemberID());
        Member foundMemberB = memberRepository.findById(memberB.getMemberID());
        assertThat(foundMemberA.getMoney()).isEqualTo(6300);
        assertThat(foundMemberB.getMoney()).isEqualTo(5200);
    }

    @Test
    @DisplayName("이체중 오류 테스트")
    void accountTransferEx() throws SQLException {
        Member memberA = new Member(MEMBER_A, 8300);
        Member memberEx = new Member(EX, 3200);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberID(), memberEx.getMemberID(), 2000))
                .isInstanceOf(IllegalArgumentException.class);

        Member foundMemberA = memberRepository.findById(memberA.getMemberID());
        Member foundMemberEx = memberRepository.findById(memberEx.getMemberID());
        assertThat(foundMemberA.getMoney()).isEqualTo(8300);
        assertThat(foundMemberEx.getMoney()).isEqualTo(3200);
    }
}