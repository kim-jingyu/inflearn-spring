package hello.exception.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

/**
 * LogFilter 에서 DispatcherType 이 어떻게 사용되는지 알아본다.
 */
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST UUID = {}, DISPATCHER_TYPE = {}, REQUEST_URI = {}", uuid, httpRequest.getDispatcherType(), requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE UUID = {}, DISPATCHER_TYPE = {}, REQUEST_URI = {}", uuid, httpRequest.getDispatcherType(), requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("LogFilter destroyed");
    }
}
