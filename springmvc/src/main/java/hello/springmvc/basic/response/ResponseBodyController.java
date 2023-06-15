package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {

    /**
     * 서블릿을 직접 다룰 때 처럼 HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 ok 응답 메시지를 전달한다.
     * response.getWriter.write("ok")
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    /**
     * HttpEntity, ResponseEntity (Http Status 추가)
     * <p>
     * ResponseEntity 는 HttpEntity 를 상속 받았다.
     * HttpEntity 는 HTTP 메시지의 헤더, 바디 정보를 가지고 있다.
     * ResponseEntity 는 여기에 더해서 HTTP 응답 코드를 설정할 수 있다.
     *
     * @return
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/response-body-string-v2-1")
    public HttpEntity<String> responseBodyV21() {
        return new HttpEntity<>("ok");
    }

    /**
     * @return
     * @ResponseBody 를 사용하면 view 를 사용하지 않고, HTTP Message Converter 를 통해서 HTTP 메시지를 직접 입력할 수 있다.
     * ResponseEntity 도 동일한 방식으로 동작한다.
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    /**
     * ResponseEntity 를 반환한다.
     * HTTP Message Converter 를 통해서 JSON 형식으로 변환되어서 반환된다.
     *
     * @return
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData data = new HelloData();
        data.setUsername("kim");
        data.setAge(20);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData data = new HelloData();
        data.setUsername("kim");
        data.setAge(20);

        return data;
    }
}
