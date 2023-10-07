package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // START LINE
        String method = req.getMethod();
        String protocol = req.getProtocol();
        String scheme = req.getScheme();
        StringBuffer requestURL = req.getRequestURL();
        String requestURI = req.getRequestURI();
        String queryString = req.getQueryString();
        boolean secure = req.isSecure(); // https 사용 유무

        // 헤더 정보
        req.getParameterNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ": " + req.getHeader(headerName)));

        // Host 편의 조회
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();

        // Accept-Language 편의 조회
        req.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " + locale));
        Locale locale = req.getLocale();

        // cookie 편의 조회
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        String contentType = req.getContentType();
        int contentLength = req.getContentLength();
        String characterEncoding = req.getCharacterEncoding();

        // 기타 정보는 HTTP 메시지 정보는 아니다.

        // Remote 정보
        String remoteHost = req.getRemoteHost();
        String remoteAddr = req.getRemoteAddr();
        int remotePort = req.getRemotePort();

        // Local 정보
        String localName = req.getLocalName();
        String localAddr = req.getLocalAddr();
        int localPort = req.getLocalPort();
    }
}
