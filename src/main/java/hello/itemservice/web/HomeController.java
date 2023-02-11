package hello.itemservice.web;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import hello.itemservice.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping
    public String home() {
        return "home";
    }

    /**
     * 세션 X
     * @CookieValue 를 사용하면 편리하게 쿠리를 조회할 수 있다.
     * 로그인 하지 않은 사용자도 홈에 접근할 수 있게 required=false 를 사용한다.
     */
//    @GetMapping
    public String homeLoginV1(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        // 로그인 쿠키가 없는 사용자는 기존 home 으로 보낸다.
        if (memberId == null) {
            return "home";
        }

        // 로그인 쿠키가 있는 사용자
        Member loginMember = memberRepository.findMemberByID(memberId);

        // 찾는 회원이 없으면
        if (loginMember == null) {
            return "home";
        }

        // 로그인 쿠키도 있고, 찾는 회원도 있으면
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    /**
     * 세션 O
     */
//    @GetMapping
    public String homeLoginV2(HttpServletRequest request, Model model) {

        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);

        // 회원 정보가 없으면 기본 화면 반환
        if (member == null) {
            return "home";
        }

        // 회원 정보가 있으면 회원 화면 반환
        model.addAttribute("member", member);
        return "loginHome";
    }

    @GetMapping
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        // 세션이 없으면 기본 화면 반환
        if (session == null) {
            return "home";
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        // 세션에 회원 데이터가 없으면 기본 화면 반환
        if (member == null) {
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", member);
        return "loginHome";
    }
}
