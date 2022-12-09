package hello.hellospring.domain;

/**
 * 회원 객체
 */
public class Member {
    private Long id;    //회원 ID - 임의의 값
    private String name;    //이름

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
