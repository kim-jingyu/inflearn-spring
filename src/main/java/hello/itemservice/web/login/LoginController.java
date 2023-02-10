package hello.itemservice.web.login;

import hello.itemservice.domain.login.LoginService;
import hello.itemservice.domain.member.Member;
import hello.itemservice.web.login.form.LoginForm;
import hello.itemservice.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;


    @GetMapping
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    /**
     * 세션 X
     */
//    @PostMapping
    public String loginV1(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {

        // 실패 로직
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        // 성공 로직
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        // 로그인 실패시
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공시

        // 쿠키에 시간 정보를 주지않으면 세션 쿠키 (브라우저 종류시 모두 종료)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/"; // Home 으로 return
    }

    /**
     * 세션 O
     */
    @PostMapping
    public String loginV2(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {

        // 검증 로직 실패시
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        // 검증 로직 성공시
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        // 로그인 실패 시
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 시

        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관 + 쿠키 발행
        sessionManager.createSession(loginMember, response);

        return "redirect:/";
    }
}
