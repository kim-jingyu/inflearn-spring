package hello.typeconverter.converters;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {


    @Override
    public IpPort convert(String source) {
        log.info("convert String source to IpPort = {}", source);
        String[] splitSources = source.split(":");
        String ip = splitSources[0];
        int port = Integer.valueOf(splitSources[1]);

        return new IpPort(ip, port);
    }
}
