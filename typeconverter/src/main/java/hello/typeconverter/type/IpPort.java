package hello.typeconverter.type;

import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class IpPort {
    private String ip;
    private int port;
}
