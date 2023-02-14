package hello.exception.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 오류 페이지를 보여주기 위한 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/error-page")
public class ErrorPageController {

    @RequestMapping("/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("404 오류 페이지를 보여주기 위한 컨트롤러 호출");
        return "error-page/404";
    }

    @RequestMapping("/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("500 오류 페이지를 보여주기 위한 컨트롤러 호출");
        return "error-page/500";
    }
}
