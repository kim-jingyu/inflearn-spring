package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String basic(Model model) {
        model.addAttribute("data", "text output test");
        model.addAttribute("escaped", "<b>escaped test</b>");
        return "basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String unescaped(Model model) {
        model.addAttribute("unescaped", "<b>unescaped test</b>");
        return "basic/text-unescaped";
    }
}
