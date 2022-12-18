package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * JPA 회원 레포지토리
 */
public class JpaMemberRepository implements MemberRepository{

//    JPA는 EntityManager로 모든 것이 동작한다. Datasource를 들고있음.
//    스프링부트가 jpa 라이브러리는 받으면 EntityManager를 생성해준다. 현재 DB와 연결
//    우리는 injection 받으면 된다.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); //영속하다. 영구 저장
        //JPA가 insert 쿼리를 다 만들어서 DB에 집어넣고, id까지 setID 해줌
        return member;
    }

//    조회
    @Override
    public Optional<Member> findById(Long id) {
        //find(조회할 타입, 식별자(pk값)
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

//    pk기반이 아닌 나머지 작업은 JPQL이라는 객체지향 쿼리 언어를 사용해야한다.
//    객체(Entity)를 대상으로 쿼리를 날린다. 그러면 이게 SQL로 번역이 된다.
//    select m from Member m -> Member 객체를 대상으로 (m은 alias)
//    Entity 자체를 select 함
//    단, JPA 기술을 Spring 에서 감싸서 제공하는 기술 -> SpringDataJPA. JPQL 사용 X
//    JPA를 사용하려면(데이터 저장 변경시) Transactional 있어야 함 -> service 계층
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
