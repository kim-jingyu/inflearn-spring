package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // START LINE
        resp.setStatus(HttpServletResponse.SC_OK);

        // response-headers
        resp.setHeader("Content-Type", "text/plain;charset=utf-8");
        resp.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("my-header", "hello");

        // header 편의 메서드

        // Content-Type: text/plain;charset=utf-8
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");

        // Content-Length: 2
        // resp.setContentLength(2);

        // Set-Cookie: myCookie=good; Max-Age=600;
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);
        resp.addCookie(cookie);

        // Status Code 302
        // Location: /basic/hello-form.html
        resp.sendRedirect("/basic/hello-form.html");
    }
}
