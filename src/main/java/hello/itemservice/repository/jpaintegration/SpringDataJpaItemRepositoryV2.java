package hello.itemservice.repository.jpaintegration;

import hello.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 스프링 데이터 JPA 의 기능을 제공하는 리포지토리
 * 기본 CRUD
 * 추가로 단순한 조회 쿼리 추가 가능
 */
public interface SpringDataJpaItemRepositoryV2 extends JpaRepository<Item, Long> {
}
