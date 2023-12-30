package hello.jdbc2.repository;

import hello.jdbc2.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void save() throws SQLException {
        // given
        Member member = new Member("1", 10000);

        // when
        repository.save(member);

        // then
    }
}