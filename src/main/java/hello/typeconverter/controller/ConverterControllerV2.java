package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ConverterControllerV2 {

    /**
     * Thymeleaf 는 rendering 시에 Converter 를 적용해서 rendering 하는 방법을 지원한다.
     * 객체를 View 로 넘기면 편리하게 문자로 변환하는 방법을 알아본다.
     */
    @GetMapping("/converter-view")
    public String converterView(Model model) {
        model.addAttribute("number", 987987);
        model.addAttribute("ipPort", new IpPort("8.8.8.8", 443));
        return "converter-view";
    }

    /**
     * IpPort 를 뷰 템플릿 폼에 출력한다.
     */
    @GetMapping("/converter/edit")
    public String converterForm(Model model) {
        IpPort ipPort = new IpPort("8.8.8.8", 443);
        IpPortDto form = new IpPortDto(ipPort);

        model.addAttribute("form", form);
        return "converter-form";
    }

    /**
     * 뷰 템플릿 폼의 IpPort 정보를 받아서 출력한다.
     */
    @GetMapping("/converter/edit")
    public String converterEdit(@ModelAttribute IpPortDto form, Model model) {
        IpPort ipPort = form.getIpPort();

        model.addAttribute("ipPort", ipPort);
        return "converter-view";
    }

    /**
     * 데이터를 전달하는 Form 객체
     */
    @Data
    @AllArgsConstructor
    static class IpPortDto {
        private IpPort ipPort;
    }

}
