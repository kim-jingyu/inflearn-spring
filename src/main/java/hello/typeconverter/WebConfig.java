package hello.typeconverter;

import hello.typeconverter.converters.IntegerToStringConverter;
import hello.typeconverter.converters.IpPortToStringConverter;
import hello.typeconverter.converters.StringToIntegerConverter;
import hello.typeconverter.converters.StringToIpPortConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 스프링에 Converter 적용하기
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 추가하고 싶은 컨버터를 등록한다.
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new IpPortToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
    }
}
