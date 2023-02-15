package hello.exception.userdefinition;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BadRequestException 예외가 컨트롤러 밖으로 넘어가면
 * ResponseStatusExceptionResolver 예외가 해당 애노테이션을 확인해서 오류 코드를 400으로 변경하고, 메시지도 담는다.
 * ResponseStatusExceptionResolver 코드도 확인해보면 결국 response.sendError(statusCode, resolvedReason)을 호출한다.
 * 즉, sendError 를 호출했기 때문에 WAS 에서 다시 오류 페이지 (/error) 를 내부 요청한다.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 요청 오류")
public class BadRequestException extends RuntimeException{
}
