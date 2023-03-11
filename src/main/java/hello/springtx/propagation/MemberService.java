package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    public void join(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("==회원 리포지토리 호출 시작==");
        memberRepository.save(member);
        log.info("==회원 리포지토리 호출 종료==");


        log.info("==로그 리포지토리 호출 시작==");
        logRepository.save(logMessage);
        log.info("==로그 리포지토리 호출 종료==");
    }

    public void joinEx(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("==회원 리포지토리 호출 시작==");
        memberRepository.save(member);
        log.info("==회원 리포지토리 호출 종료==");


        log.info("==로그 리포지토리 호출 시작==");
        try {
            logRepository.save(logMessage);
        } catch (RuntimeException e) {
            log.info("로그 저장에 실패했습니다. 로그 메서지 = {}", logMessage);
            log.info("정상 흐름 반환");
        }
        log.info("==로그 리포지토리 호출 종료==");
    }


}
