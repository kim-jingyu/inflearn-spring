package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * Content-Type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    /**
     * 문자로 된 JSON 데이터를 Jackson 라이브러리인 objectMapper 를 사용해서 자바 객체로 변환한다.
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * HttpServletRequest 를 사용해서 직접 HTTP 메시지 바디에서 데이터를 읽어와서(inputStream), 문자로 변환한다.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        response.getWriter().write("ok");
    }

    /**
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @param httpEntity
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/request-body-json-v2-1")
    public HttpEntity<String> requestBodyJsonV21(HttpEntity<String> httpEntity) throws JsonProcessingException {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return new HttpEntity<>("ok");
    }

    /**
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @param messageBody
     * @return
     * @throws JsonProcessingException
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2-2")
    public String requestBodyJsonV22(@RequestBody String messageBody) throws JsonProcessingException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    /**
     * @param data
     * @return
     * @RequestBody 객체 변환 ( @RequestBody 생략 불가능 -> @ModelAttribute가 적용되어 버림)
     * @RequestBody 에 직접 만든 객첼르 지정할 수 있다.
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter
     * Content-Type: application/json
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    /**
     * @RequestBody 요청
     *  - JSON 요청 -> HTTP 메시지 컨버터 -> 객체
     * @ResponseBody 응답
     *  - 객체 -> HTTP 메시지 컨버터 -> JSON 응답
     * @param httpEntity
     * @return
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public HelloData requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return data;
    }
}
