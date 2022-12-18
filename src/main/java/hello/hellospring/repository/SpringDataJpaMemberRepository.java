package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * SpringDataJpa 가 JpaRepository를 받고있으면 구현체를 자동으로 만들어줌
 * 그리고 스프링 빈에 자동으로 등록해줌
 * 우리는 그것을 그냥 가져다가 쓰면 된다. -> SpringConfig
 */
//Long -> pk
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

//    JPQL -> select m from Member m where m.name = ?
//    인터페이스 이름 만으로도 개발이 끝남
    @Override
    Optional<Member> findByName(String name);
}
