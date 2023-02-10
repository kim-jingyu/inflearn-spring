package hello.itemservice.web.login;

import hello.itemservice.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {

    private final SessionManager sessionManager;

    /**
     * 세션X
     * 로그아웃 기능
     * 서버에서 해당 쿠키의 종료 날짜를 0으로 지정
     *
     * @param response
     * @return
     */
//    @PostMapping
    public String logoutV1(HttpServletResponse response) {
        expireCookie(response,"memberId");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response,String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 세션O
     *
     * @param request
     * @return
     */
//    @PostMapping
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expireSession(request);
        return "redirect:/";
    }

    /**
     * HttpSession - 세션 O
     */
    @PostMapping
    public String logoutV3(HttpServletRequest request) {
        // 세션 삭제
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
