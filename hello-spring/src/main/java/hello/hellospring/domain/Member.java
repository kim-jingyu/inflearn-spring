package hello.hellospring.domain;

import javax.persistence.*;

/**
 * 회원 객체
 * JPA 엔티티 매핑 -> JPA가 관리하는 엔티티
 */

@Entity
public class Member {

//    이 어노테이션들을 가지고 데이터베이스와 매핑함
//    이 정보를 가지고 insert,delete,select 문 수행
//    이 이후에 JPA 레포지토리 만들기
//    PK 매핑, identity 전략 -> DB에 값을 넣으면 DB가 자동으로 Id 생성
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //회원 ID - 임의의 값

//    DB의 column명 따라서 매핑. 예를들어, column명이 username이라면
//    @Column(name = "username")
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
