package hello.springmvc.basic;

import lombok.Data;

/**
 * 요청 파라미터를 바인딩 받을 객체
 */
@Data // Getter, Setter, toString, RequiredArgsConstructor, EqualsAndHashCode 를 자동 적용해준다.
public class HelloData {
    String username;
    int age;
}
