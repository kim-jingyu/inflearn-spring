package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    @Autowired
    public MemberServiceTest(MemberService memberService, MemberRepository memberRepository, LogRepository logRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.logRepository = logRepository;
    }

    /**
     * MemberService    @Transaction : OFF
     * MemberRepository @Transaction : ON
     * LogRepository    @Transaction : ON
     */
    @Test
    void 외부_트랜잭션_OFF_성공() {
        String username = "외부 트랜잭션 OFF 성공";

        memberService.join(username);

        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * MemberService    @Transaction : OFF
     * MemberRepository @Transaction : ON
     * LogRepository    @Transaction : ON Exception
     */
    @Test
    void 외부_트랜잭션_OFF_실패() {
        String username = "로그예외 외부 트랜잭션 OFF 실패";

        assertThatThrownBy(() -> memberService.join(username))
                .isInstanceOf(RuntimeException.class);

        assertTrue(memberRepository.find(username).isPresent());
        // 로그 리포지토리에서 런타임 예외가 발생해 롤백 처리되었다.
        assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * MemberService    @Transaction : ON
     * MemberRepository @Transaction : OFF
     * LogRepository    @Transaction : OFF
     */
    @Test
    void 외부_트랜잭션_ON() {
        String username = "외부 트랜잭션 ON";

        memberService.join(username);

        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * MemberService    @Transaction : ON
     * MemberRepository @Transaction : ON
     * LogRepository    @Transaction : ON
     */
    @Test
    void 모든_트랜잭션_ON() {
        String username = "모든 트랜잭션 ON";

        memberService.join(username);

        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * MemberService    @Transaction : ON
     * MemberRepository @Transaction : ON
     * LogRepository    @Transaction : ON Exception
     */
    @Test
    void 모든_트랜잭션_ON_롤백() {
        String username = "로그예외 모든 트랜잭션 ON";

        assertThatThrownBy(() -> memberService.join(username))
                .isInstanceOf(RuntimeException.class);

        assertTrue(memberRepository.find(username).isEmpty());
        assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * MemberService    @Transaction : ON 예외 처리
     * MemberRepository @Transaction : ON
     * LogRepository    @Transaction : ON Exception
     */
    @Test
    void 모든_트랜잭션_ON_외부_트랜잭션_예외처리() {
        String username = "로그예외_recoverException_fail";

        assertThatThrownBy(() -> memberService.joinEx(username))
                .isInstanceOf(UnexpectedRollbackException.class);

        /**
         * rollbackOnly 옵션이 설정되어 모든 데이터가 롤백된다.
         */
        assertTrue(memberRepository.find(username).isEmpty());
        assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * MemberService    @Transaction : ON 예외 처리
     * MemberRepository @Transaction : ON
     * LogRepository    @Transaction(REQUIRES_NEW) : ON Exception
     */
    @Test
    void 모든_트랜잭션_ON_로그_트랜잭션_옵션처리() {
        String username = "로그예외_recoverException_success";

        memberService.joinEx(username);

        /**
         * rollbackOnly 옵션이 설정되어 모든 데이터가 롤백된다.
         */
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isEmpty());
    }

}