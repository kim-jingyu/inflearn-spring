package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 넣어주면, view는 조회가 안된다.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    /**
     * @param username
     * @param age
     * @return
     * @RequestParam : 파라미터 이름으로 바인딩, name(value)의 속성이 파라미터 이름으로 사용됨
     * @ResponseBody : View 조회를 무시하고, HTTP message body 에 직접 해당 내용을 입력함.
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String username,
            @RequestParam("age") int age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    /**
     * @param username
     * @param age
     * @return
     * @RequestParam 애노테이션을 생략하면 스프링 MVC 는 내부에서 required=false 를 적용한다.
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    /**
     * 1. 파라미터 이름만 있고 값이 없는 경우 -> 빈문자로 통과. /request-param?username=
     * 2. username 파라미터가 없는 경우 -> 400 Bad Request Error 발생
     * 3. 기본형(primitive) int 에 Null 을 입력할 경우 -> null 을 int 에 입력받는 것을 불가능 (500 Inter Server Error 발생)
     * -> 1. null 을 받을 수 있는 Integer 로 변경하거나,
     * -> 2. defaultValue 속성을 사용한다.
     *
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    /**
     * 1. 파라미터에 값이 없는 경우 defaultValue 를 사용하면 기본 값을 적용할 수 있다.
     * 2. 이미 기본 값이 있기 때문에 required 는 의미가 없다.
     * 3. defaultValue 는 빈 문자의 경우에도 설정한 기본 값이 적용된다. -> /request-param-default?username=
     *
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(defaultValue = "guest") String username,
            @RequestParam(defaultValue = "-1") int age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap) {

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }
}
