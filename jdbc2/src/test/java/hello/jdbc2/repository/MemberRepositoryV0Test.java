package hello.jdbc2.repository;

import hello.jdbc2.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("1", 10000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getId());
        assertThat(findMember).isEqualTo(member);
    }
}