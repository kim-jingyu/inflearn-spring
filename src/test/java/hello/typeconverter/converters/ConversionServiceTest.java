package hello.typeconverter.converters;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.*;

/**
 * Conversion Service Test
 */
public class ConversionServiceTest {

    @Test
    void conversionService() {
        // 등록
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 사용
        assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10); // "10" -> 10
        assertThat(conversionService.convert(10, String.class)).isEqualTo("10"); // 10 -> "10"
        assertThat(conversionService.convert("8.8.8.8:443", IpPort.class)).isEqualTo(new IpPort("8.8.8.8", 443)); // string -> IpPort

        IpPort ipPort = new IpPort("1.1.1.1", 80);
        assertThat(conversionService.convert(ipPort, String.class)).isEqualTo("1.1.1.1:80"); // IpPort -> String
    }
}
