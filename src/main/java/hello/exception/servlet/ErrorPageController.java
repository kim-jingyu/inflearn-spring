package hello.exception.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 오류 페이지를 보여주기 위한 컨트롤러 ( 오류 출력 )
 */
@Slf4j
@Controller
@RequestMapping("/error-page")
public class ErrorPageController {

    // RequestDispatcher 에 상수로 정의되어 있음.
    // request.attribute 에 WAS 서버가 담아준 정보
    public static final String ERROR_EXCEPTION = "jakarta.servlet.error.exception"; // 예외
    public static final String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type"; // 예외 타입
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message"; // 오류 메시지
    public static final String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri"; // 클라이언트 요청 URI
    public static final String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name"; // 오류가 발생한 서블릿 이름
    public static final String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code"; // HTTP 상태 코드

    @RequestMapping("/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("404 오류 페이지를 보여주기 위한 컨트롤러 호출");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("500 오류 페이지를 보여주기 위한 컨트롤러 호출");
        printErrorInfo(request);
        return "error-page/500";
    }

    // request.attribute 에 WAS 서버가 담아준 정보 출력
    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: ", request.getAttribute(ERROR_EXCEPTION)); //예외
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE)); // 예외 타입
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE)); // 오류 메시지
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI)); // 클라이언트 요청 URL
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME)); // 오류가 발생한 서블릿 이름
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE)); // HTTP 상태 코드
        log.info("dispatcherType: {}", request.getDispatcherType());
    }
}
