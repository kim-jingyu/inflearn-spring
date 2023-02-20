package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // Connection Pooling: HikariProxyConnection -> JdbcConnection
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException, InterruptedException {
        log.info("Start");

        // 회원 등록
        // JDBC 로 회원을 DB 에 등록한다.
        Member memberA = new Member("MemberA", 10000);
        repository.save(memberA);

        // 회원 조회
        Member foundfindMember = repository.findMember(memberA.getMemberID());
        System.out.println("findMember = " + foundfindMember + "memberA = " + memberA);
        assertThat(foundfindMember).isEqualTo(memberA);

        // 회원 수정 : money (10000) -> (5000)
        repository.updateMember(memberA.getMemberID(), 5000);
        Member updatedMember = repository.findMember(memberA.getMemberID());
        assertThat(updatedMember).isEqualTo(new Member(memberA.getMemberID(), 5000));

        // 회원 삭제
        repository.delete(memberA.getMemberID());
        assertThatThrownBy(() -> repository.findMember(memberA.getMemberID()))
                .isInstanceOf(NoSuchElementException.class);

        Thread.sleep(1000);
    }
}