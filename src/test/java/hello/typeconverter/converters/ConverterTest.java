package hello.typeconverter.converters;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ConverterTest {

    @Test
    void stringToIpPort() {
        StringToIpPortConverter stringToIpPortConverter = new StringToIpPortConverter();
        String source = "8.8.8.8:443";
        IpPort convertedResult = stringToIpPortConverter.convert(source);

        assertThat(convertedResult).isEqualTo(new IpPort("8.8.8.8", 443));
    }

    @Test
    void IpPortToString() {
        IpPortToStringConverter ipPortToStringConverter = new IpPortToStringConverter();
        IpPort ipPort = new IpPort("7.7.7.7", 21);
        String convertedIpPort = ipPortToStringConverter.convert(ipPort);

        assertThat(convertedIpPort).isEqualTo("7.7.7.7:21");
    }
}