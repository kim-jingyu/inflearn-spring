package hello.jdbc2.service;

import hello.jdbc2.domain.Member;
import hello.jdbc2.repository.MemberRepositoryV2;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class MemberServiceV2 {
    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public MemberServiceV2(DataSource dataSource, MemberRepositoryV2 memberRepository) {
        this.dataSource = dataSource;
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            connection.setAutoCommit(false);
            Member fromMember = memberRepository.findById(fromId);
            Member toMember = memberRepository.findById(toId);

            memberRepository.update(connection, fromId, fromMember.getMoney() - money);
            validation(toMember);
            memberRepository.update(connection, toId, toMember.getMoney() + money);
        } catch (SQLException e) {
            connection.rollback();
            throw new IllegalStateException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (Exception e) {
                    log.info("error", e);
                }
            }
        }
    }

    private static void validation(Member toMember) {
        if (toMember.getId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
