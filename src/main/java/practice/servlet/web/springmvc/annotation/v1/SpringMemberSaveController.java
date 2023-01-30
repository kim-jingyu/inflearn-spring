package practice.servlet.web.springmvc.annotation.v1;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import practice.servlet.domain.member.Member;
import practice.servlet.domain.member.MemberRepository;

import java.util.List;

@Controller
public class SpringMemberSaveController {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/members/save")
    public ModelAndView process(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.signup(member);

        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("user", member);
        return mv;
    }
}
