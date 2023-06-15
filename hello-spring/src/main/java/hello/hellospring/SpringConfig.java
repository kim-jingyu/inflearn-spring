package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    /**
     * SpringDataJpa가 만들어놓은 구현체가 등록이 된다. -> memberRepository
     * 그리고 memberService에 의존 관계를 세팅해줘야 함
     * 그러면 스프링 컨테이너에서 memberRepository를 찾음 근데 내가 등록해놓은게 없음
     * -> SpringDataJpa에서 interface만 만들어놓으면 구현체를 만들어놓고 스프링 빈에 등록함
     * 그래서 우리는 인젝션을 받을 수 있음 -> memberService 등록하면 됨
     */
    private final MemberRepository memberRepository;

//    생성자가 하나면 @Autowired 어노테이션 생략 가능
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * EntityManager가 필요함
     */
//    @PersistenceContext // 스펙에서는 이렇게 해야함.
//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    /**
     * DataSource는 데이터베이스 커넥션을 획득할 때 사용하는 객체다
     * 스프링 부트는 데이터베이스 커넥션
     * 정보를 바탕으로 DataSource를 생성하고 스프링 빈으로 만들어둔다.
     * 그래서 DI를 받을 수 있다.
     */
//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    /**
     * 스프링 데이터 jpa 사용시, 인젝션 받은 것을 memberService에 의존관계 세팅
     * memberRepository() -> memberRepository
     */
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//    }
}
