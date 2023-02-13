package hello.itemservice.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * 요청 로그 인터셉터
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "uuid";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // 요청 로그를 구분하기 위한 uuid 생성
        String uuid = UUID.randomUUID().toString();

//        preHandle 에서 지정한 값을 postHandle 및 afterCompletion 에서 사용하려면 어딘가에 담아두어야 한다.
//        LogInterceptor 도 싱글톤처럼 사용되기 때문에 멤버변수를 사용하면 위험하다.
//        따라서 uuid 를 request 에 담아두고 뷰 렌더링 호출 이후 afterCompletion 에서 getAttribute 로 찾아서 사용한다.
        request.setAttribute(LOG_ID, uuid);

        /**
         * 핸들러 정보는 어떤 핸들러 매핑을 사용하는가에 따라 달라진다.
         * 스프링을 사용하면 일반적으로 @Controller, @RequestMapping 을 활용한 핸들러 매핑을 사용하는데, 이 경우 핸들러 정보로 HandlerMethod 가 넘어온다.
         * @Controller 가 아니라 /resources/static 과 같은 정적 리소스가 호출되는 경우, ResourceHttpRequestHandler 가 핸들러 정보로 넘어오기 때문에 타입에 따른 처리가 필요하다.
         *
         * @RequestMapping: HandlerMethod
         * 정적 리소스: ResourceHttpRequestHandler
         */
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러 메소드의 모든 정보가 포함되어 있다.
        }

        log.info("REQUEST UUID = {}, URL = {}, Handler = {}", uuid, requestURI, handler);
        return true; // 정상 호출. 다음 인터셉터나 컨트롤러가 호출된다.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle modelAndView = {}", modelAndView);
    }

    /**
     * afterCompletion 은 예외가 발생해도 호출되는 것을 보장한다.
     * 종료 로그 호출
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID); // 요청 로그 구분을 위한 uuid
        log.info("RESPONSE UUID = {}, URL = {}", logId, requestURI);
        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
