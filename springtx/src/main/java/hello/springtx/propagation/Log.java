package hello.springtx.propagation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * JPA 를 통해 관리하는 로그 엔티티
 */
@Entity
@Getter
@Setter
public class Log {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    public Log() {
    }

    public Log(String message) {
        this.message = message;
    }
}
