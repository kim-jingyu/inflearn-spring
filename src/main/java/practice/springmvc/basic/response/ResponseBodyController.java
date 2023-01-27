package practice.springmvc.basic.response;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import practice.springmvc.basic.HelloData;

import java.io.IOException;

/**
 * API
 */
@Slf4j
@Controller
public class ResponseBodyController {

    @GetMapping("/response-body-string-servlet")
    public void responseBodyStringServlet(HttpServletResponse response) throws IOException {
        response.getWriter().write("hello, spring !");
    }

    @GetMapping("/response-body-string-http-entity")
    public ResponseEntity<String> responseBodyStringHttpEntity() {
        return new ResponseEntity<>("hello, spring ~", HttpStatus.OK);
    }

    @GetMapping("/response-body-json-response-entity")
    public ResponseEntity<HelloData> responseBodyJsonEntity() {
        HelloData helloData = new HelloData();
        helloData.setUsername("response-entity");
        helloData.setAge(21);

        return new ResponseEntity<>(helloData, HttpStatus.CREATED);
    }

    @ResponseStatus
    @ResponseBody
    @GetMapping("/response-body-json-response-body")
    public HelloData responseBodyJsonResponseBody() {
        HelloData helloData = new HelloData();
        helloData.setUsername("response-body");
        helloData.setAge(35);

        return helloData;
    }
}
