package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * - 실제 로그를 시작하고 종료하기
 *  - 로그를 출력하고 실행시간도 측정하기
  */
@Slf4j
@Component
public class HelloTraceV1 {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    /**
     * 로그 시작
     * 로그 메시지를 파라미터로 받아서 로그 출력
     * 응답 결과로 현재 로그의 상태인 TraceStatus 를 반환
     */
    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    /**
     * 로그 정상 종료
     * 파라미터로 시작 로그의 상태를 전달받는다.
     * 이 값을 활용해서 실행시간을 계산하고, 종료시에도 시작할 때와 동일한 로그 메시지를 출력할 수 있다.
     * 정상 흐름에서 호출한다.
     */
    public void end(TraceStatus status) {
        complete(status, null);
    }

    /**
     * 로그를 예외 상황으로 종료
     * TraceStatus, Exception 정보를 함께 전달받아서 실행시간, 예외 정보를 포함한 결과 로그를 출력한다.
     * 예외가 발생했을때 호출
     */
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    /**
     * end(), exception() 의 요청 흐름을 한 곳에서 편리하게 처리한다.
     * 1. 실행 시간을 측정
     * 2. 로그 남기기
     */
    private void complete(TraceStatus status, Exception e) {
        TraceId traceId = status.getTraceId();
        long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();

        // 정상 종료시
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            // 예외 발생시
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }

    /**
     * level 에 따른 prefix 출력
     */
    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == (level - 1)) ? "|" + prefix : "|  ");
        }
        return sb.toString();
    }
}
