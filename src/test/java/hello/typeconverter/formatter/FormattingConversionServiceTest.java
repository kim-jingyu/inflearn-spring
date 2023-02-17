package hello.typeconverter.formatter;

import hello.typeconverter.converters.IpPortToStringConverter;
import hello.typeconverter.converters.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.*;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {
        // 포맷팅을 지원하는 컨버징 서비스
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        // 컨버터 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 포맷터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        // 컨버터 사용
        IpPort ipPort = conversionService.convert("8.8.8.8:443", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("8.8.8.8", 443));

        // 포맷터 사용
        String convertToString = conversionService.convert(10000, String.class);
        assertThat(convertToString).isEqualTo("10,000");
        Integer convertToInteger = conversionService.convert("10,000", Integer.class);
        assertThat(convertToInteger).isEqualTo(10000);
    }
}
