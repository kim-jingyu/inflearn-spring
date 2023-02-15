package hello.exception.api;

import hello.exception.userdefinition.BadRequestException;
import hello.exception.userdefinition.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * API 예외 컨트롤러
 */
@Slf4j
@RestController
public class ApiExceptionController {

    /**
     * 회원 조회 기능
     * @param id
     * @return 객체
     */
    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    /**
     * @ResponseStatus 애노테이션 사용한 예외 발생
     * @return
     */
    @GetMapping("/api/response-status-400")
    public String responseStatus400() {
        throw new BadRequestException();
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String memberId;
        private String name;
    }
}
