package hello.jdbc2.domain;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String memberId;
    private int money;
}
