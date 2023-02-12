package hello.itemservice.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

/**
 * 로그 필터
 */
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init. 필터 초기화 메서드");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 다운 캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestURI = httpRequest.getRequestURI();

        // HTTP 요청을 구분하기 위해 요청당 임의의 uuid 를 생성해둔다.
        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST UUID = {}, URL = {}", uuid, requestURI);

            // 다음 필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿을 호출한다.
            // 만약 이 로직을 호출하지 않으면 다음 단계로 진행되지 않는다.
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; // 예외를 다음 단계로 던진다. ( throw Exception to next level )
        } finally {
            log.info("RESPONSE UUID = {}, URL = {}", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy. 필터 종료 메서드");
    }
}
