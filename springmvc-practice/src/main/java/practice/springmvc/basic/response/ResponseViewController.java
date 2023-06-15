package practice.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 뷰 템플릿을 호출하는 컨트롤러
 */
@Controller
public class ResponseViewController {

    @RequestMapping("/response-model-and-view")
    public ModelAndView responseModelAndView() {
        ModelAndView mav = new ModelAndView("response/hello").addObject("data", "hello, spring !");
        return mav;
    }

    @RequestMapping("/response-model")
    public String responseModel(Model model) {
        model.addAttribute("data", "hello, spring ?");
        return "response/hello";
    }

    @RequestMapping("/response/hello")
    public void responseHello(Model model) {
        model.addAttribute("data", "hello, spring ~");
    }
}
