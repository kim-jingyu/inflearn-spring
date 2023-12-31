package hello.jdbc2.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc2.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc2.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV1Test {
    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager
        // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // Connection Pooling : HikariProxyConnection -> JdbcConnection
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("1", 10000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getId());
        assertThat(findMember).isEqualTo(member);

        // update
        repository.update(member.getId(), 20000);
        Member updatedMember = repository.findById(member.getId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getId());
        assertThatThrownBy(() -> repository.findById(member.getId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}