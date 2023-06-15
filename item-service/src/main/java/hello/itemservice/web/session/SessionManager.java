package hello.itemservice.web.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    // 세션 저장소
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response) {
        // 세션 ID 생성 (임의의 추정 불가능한 랜덤 값)
        String sessionId = UUID.randomUUID().toString();

        // 세션 저장소에 sessionId와 보관할 값(회원 객체) 저장
        sessionStore.put(sessionId, value);

        // sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request) {
        // 클라이언트가 요청한 sessionId 쿠키 값으로 세션 저장소에 보관한 값 조회
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        // 클라이언트가 요청한 쿠키가 없으면 null 반환
        if (sessionCookie == null) {
            return null;
        }

        // 클라이언트가 요청한 쿠키가 있으면 세션 저장소에 보관한 값 반환
        return sessionStore.get(sessionCookie.getValue());
    }

    /**
     * 세션 만료
     */
    public void expireSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    private Cookie findCookie(HttpServletRequest request,String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .orElse(null);
    }
}
