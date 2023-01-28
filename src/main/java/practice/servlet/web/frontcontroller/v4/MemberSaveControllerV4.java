package practice.servlet.web.frontcontroller.v4;

import org.springframework.ui.Model;
import practice.servlet.domain.member.Member;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4{
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        model.put("user", member);

        return "save-result";
    }
}
