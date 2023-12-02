package hello.jdbc2.repository;

import hello.jdbc2.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void saveTest() throws SQLException {
        Member member = new Member("memberV0", 10000);
        repository.save(member);
    }

    @Test
    void findByIdTest() throws SQLException {
        Member findMember = repository.findById("memberV0");
        log.info("findMember = {}", findMember);
        assertThat(findMember.getMemberId()).isEqualTo("memberV0");
    }
}