package practice.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class RequestBodyStringController {

    @PostMapping("/request-body-string-servlet")
    public void requestBodyStringServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-stream")
    public void requestBodyStringStream(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-http-entity")
    public HttpEntity<String> requestBodyStringHttpEntity(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        HttpHeaders headers = httpEntity.getHeaders();
        log.info("messageBody={}, header={}", messageBody, headers);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    @PostMapping("/request-body-string-http-entity2")
    public HttpEntity<String> requestBodyStringHttpEntity2(RequestEntity<String> requestEntity) {
        String messageBody = requestEntity.getBody();
        HttpMethod httpMethod = requestEntity.getMethod();
        String method = httpMethod.toString();
        URI httpUrl = requestEntity.getUrl();
        String url = httpUrl.toString();
        Type httpType = requestEntity.getType();
        String type = httpType.toString();
        String typeName = httpType.getTypeName();
        log.info("messageBody={}, method={}, url={}, type={}, typeName={}", messageBody, method, url, type, typeName);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/request-body-string-requestbody")
    public String requestBodyStringRequestBody(@RequestBody String messageBody) { // 헤더 정보가 필요하다면 HttpEntity 를 사용하거나, @RequestHeader 를 사용하면 된다.
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
