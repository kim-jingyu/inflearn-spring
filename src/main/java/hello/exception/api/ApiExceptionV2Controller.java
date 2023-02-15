package hello.exception.api;

import hello.exception.result.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgExHandle(IllegalArgumentException e) {
        log.error("IllegalArg Exception 예외 발생!!", e);
        return new ErrorResult("BAD REQUEST", e.getMessage());
    }
}
