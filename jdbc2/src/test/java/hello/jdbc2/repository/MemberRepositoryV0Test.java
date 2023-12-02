package hello.jdbc2.repository;

import hello.jdbc2.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

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

    @Test
    void updateMember() throws SQLException {
        // memberV0 : money 10000 -> 30000
        repository.update("memberV0", 30000);

        Member findMember = repository.findById("memberV0");
        assertThat(findMember.getMoney()).isEqualTo(30000);
    }

    @Test
    void deleteMember() throws SQLException {
        // memberV0 삭제
        repository.delete("memberV0");

        assertThatThrownBy(() -> repository.findById("memberV0"))
                .isInstanceOf(NoSuchElementException.class);
    }
}