package hello.jdbc2.repository;

import hello.jdbc2.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

/**
 * JDBC - DriverManager
 */
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) {
        String sql = "insert into member values (?, ?)";

        Connection connection = null;
        return null;
    }

}
