package practice.servlet.web.frontcontroller.v4;

import org.springframework.ui.Model;
import practice.servlet.domain.member.Member;
import practice.servlet.domain.member.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4{
    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.signup(member);

        model.put("user", member);

        return "save-result";
    }
}
