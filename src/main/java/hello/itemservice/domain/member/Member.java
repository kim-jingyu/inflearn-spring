package hello.itemservice.domain.member;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Member {
    private Long id;
    private String loginId;
    private String name;
    private String password;
}
