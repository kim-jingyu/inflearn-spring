package hello.jdbc2.repository;

import hello.jdbc2.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void saveTest() throws SQLException {
        Member member = new Member("memberV0", 10000);
        repository.save(member);
    }
}