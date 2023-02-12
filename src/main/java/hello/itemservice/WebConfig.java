package hello.itemservice;

import hello.itemservice.web.filter.LogFilter;
import hello.itemservice.web.filter.LoginCheckFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 필터 설정
 */
@Configuration
public class WebConfig {

    /**
     * 필터를 등록하는 방법
     * 스프링 부트를 사용한다면 FilterRegistrationBean 을 사용해서 등록
     * @return
     */
    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        // 등록할 필터 지정
        filterRegistrationBean.setFilter(new LogFilter());
        // 필터 체인 순서
        filterRegistrationBean.setOrder(1);
        // 필터를 적용할 URL 패턴 지정, 한번에 여러 패턴 지정 가능
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        // 로그인 필터 등록
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        // 필터 체인 순서
        filterRegistrationBean.setOrder(2);
        // 모든 요청에 로그인 필터 적용 -> 구현 필터 내부에서 whitelist 로 제외
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
