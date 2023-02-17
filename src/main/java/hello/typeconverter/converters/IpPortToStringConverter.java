package hello.typeconverter.converters;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort, String> {
    @Override
    public String convert(IpPort source) {
        log.info("convert IpPort source to String = {}", source);
        return source.getIp() + ":" + source.getPort();
    }
}
