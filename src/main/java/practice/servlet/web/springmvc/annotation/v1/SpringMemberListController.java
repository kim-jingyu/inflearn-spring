package practice.servlet.web.springmvc.annotation.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import practice.servlet.domain.member.Member;
import practice.servlet.domain.member.MemberRepository;

import java.util.List;

@Controller
public class SpringMemberListController {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/members")
    public ModelAndView process() {
        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("users", members);
        return mv;
    }
}
