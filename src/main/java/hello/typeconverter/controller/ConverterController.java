package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ConverterController {

    @GetMapping("/servlet-param-string-to-integer")
    public String v1(HttpServletRequest request) {

        // data 라는 파라미터 받아오기
        String data = request.getParameter("data");

        // data 를 Integer Type 으로 형변환 하기
        Integer intData = Integer.valueOf(data);

        log.info("intData = {}", intData);

        return "ok";
    }

    /**
     * 스프링이 중간에서 타입을 변환해주었다. ( String -> Integer )
     *
     * @param data
     * @return
     */
    @GetMapping("/request-param-string-to-integer")
    public String v2(@RequestParam Integer data) {
        log.info("@RequestParam data = {}", data);
        log.info("@RequestParam data.getClass().getName() = {}", data.getClass().getName());
        return "ok";
    }

    @GetMapping("/path-variable-string-to-integer/{data}")
    public String v3(@PathVariable Integer data) {
        log.info("@PathVariable data = {}", data);
        log.info("@PathVariable data.getClass().getName() = {}", data.getClass().getName());
        return "ok";
    }

    @RequestMapping("/model-attribute-string-to-integer")
    public String v4(@ModelAttribute("data") UserData userData) {
        log.info("@ModelAttribute UserData.data = {}", userData.data);
        log.info("@ModelAttribute userData.data.getClass().getName() = {}", userData.data.getClass().getName());
        return "ok";
    }

    @Getter
    @Setter
    static class UserData {
        private Integer data;
    }

    /**
     * @RequestParam 을 처리하는 ArgumentResolver 인 RequestParamMethodArgumentResolver 에서
     * ConversionService 를 사용해서 타입을 변환한다.
     */
    @GetMapping("/ip-port")
    public String ipPort(@ModelAttribute IpPort ipPort) {
        log.info("ip = {}", ipPort.getIp());
        log.info("port = {}", ipPort.getPort());
        return "ok";
    }
}
