package practice.servlet.web.springmvc.annotation.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberFormController {

    @RequestMapping("/springmvc/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
