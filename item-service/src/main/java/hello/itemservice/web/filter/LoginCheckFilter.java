package hello.itemservice.web.filter;

import hello.itemservice.web.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    // 홈 화면, 회원 가입, 로그인, 로그 아웃, css 리소스 제외
    private static final String[] whitelist = {"/", "/member/add", "/login", "/logout", "/css/*"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작. Request URL = {}", requestURI);

            // 화이트 리스트에 있는 경우 인증 체크 하지않음.
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 시작. Request URL = {}", requestURI);

                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 URL = {}", requestURI);

                    // 미인증 사용자가 비허가 URL 로 접근시 로그인 화면으로 redirect
                    // login 컨트롤러에서 redirectURL 파라미터를 받아서 로그인 성공시 해당 경로로 이동하는 기능 추가 개발 필요
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);

                    // 미인증 사용자는 다음으로 진행하지 않고 끝.
                    return;
                }
            }

            // 인증된 사용자 -> 다음으로 진행
            chain.doFilter(request, response);

        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 = {}", requestURI);
        }
    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
