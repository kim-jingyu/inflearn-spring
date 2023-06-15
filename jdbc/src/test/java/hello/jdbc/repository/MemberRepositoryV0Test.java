package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    MemberRepositoryV0 memberRepositoryV0 = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // 회원 등록
        // JDBC 로 회원을 DB 에 등록한다.
        Member memberA = new Member("MemberA", 10000);
        memberRepositoryV0.save(memberA);

        // 회원 조회
        Member foundfindMember = memberRepositoryV0.findMember(memberA.getMemberID());
        System.out.println("findMember = " + foundfindMember + "memberA = " + memberA);
        assertThat(foundfindMember).isEqualTo(memberA);

        // 회원 수정 : money (10000) -> (5000)
        memberRepositoryV0.updateMember(memberA.getMemberID(), 5000);
        Member updatedMember = memberRepositoryV0.findMember(memberA.getMemberID());
        assertThat(updatedMember).isEqualTo(new Member(memberA.getMemberID(), 5000));

        // 회원 삭제
        memberRepositoryV0.delete(memberA.getMemberID());
        assertThatThrownBy(() -> memberRepositoryV0.findMember(memberA.getMemberID()))
                .isInstanceOf(NoSuchElementException.class);
    }
}