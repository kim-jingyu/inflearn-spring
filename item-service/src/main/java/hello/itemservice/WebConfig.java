package hello.itemservice;

import hello.itemservice.web.argumentresolver.LoginMemberArgumentResolver;
import hello.itemservice.web.filter.LogFilter;
import hello.itemservice.web.filter.LoginCheckFilter;
import hello.itemservice.web.interceptor.LogInterceptor;
import hello.itemservice.web.interceptor.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 필터 설정
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())                   // 인터셉터를 등록한다.
                .order(1)                                               // 인터셉터의 호출 순서를 지정한다.
                .addPathPatterns("/**")                                 // 인터셉터를 적용할 URL 패턴을 지정한다.
                .excludePathPatterns("/css/**", "/*.ico", "/error");    // 인터셉터에서 제외할 패턴을 지정한다.

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error");
    }

    /**
     * 필터를 등록하는 방법
     * 스프링 부트를 사용한다면 FilterRegistrationBean 을 사용해서 등록
     * @return
     */
//    @Bean
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

//    @Bean
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
