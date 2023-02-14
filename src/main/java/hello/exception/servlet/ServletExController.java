package hello.exception.servlet;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

/**
 * 서블릿 예외 컨트롤러
 */
@Slf4j
@Controller
public class ServletExController {

    /**
     * Controller 에서 Exception 발생 시
     */
    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("Internal Server Error 예외 발생!");
    }

    /**
     * 오류가 발생했을 때 sendError 메서드 사용 시
     */
    @GetMapping("/error-404")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND,"404 예외 발생 ㅠㅜ");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"500 예외 발생 ㅠㅠ");
    }
}
