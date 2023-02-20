package hello.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 ID와 해당 회원이 소지한 금액을 표현
 * DB - member table 에 데이터를 저장하고 조회할 때 사용
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
    private String memberID;
    private int money;
}
