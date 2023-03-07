package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 기본적인 CRUD 기능을 사용할 수 있다.
 * 하지만 이름으로 검색하거나, 가격으로 검색하는 기능은 공통으로 제공할 수 있는 기능이 아니다.
 * 따라서 쿼리 메서드 기능을 이용하거나, @Query 를 사용해서 직접 쿼리를 실행한다.
 */
public interface SpringDataJpaItemRepository extends JpaRepository<Item, Long> {
    /** 모든 데이터 조회 - findAll()
     * 코드에는 보이지 않지만 JpaRepository 가 제공하는 기능
     * 모든 Item 을 조회
     * select i from Item i
     */

    /**
     * 이름 조건만 검색했을 때 사용하는 쿼리 메서드
     * select i from Item i where i.name like ?
     */
    List<Item> findByItemNameLIke(String itemName);

    /**
     * 가격 조건만 검색했을 떄 사용하는 쿼리 메서드
     * select i from Item i where i.price <= ?
     */
    List<Item> findByPriceLessThanEqual(Integer price);

    /**
     * 이름과 가격 조건을 검색했을 때 사용하는 쿼리 메서드
     * select i from Item i where i.itemName like ? and i.price <= ?
     */
    List<Item> findByItemNameLikeAndPriceLessThanEqual(String itemName, Integer price);

    /**
     * 이름과 가격 조건을 검색했을 때 사용하는 쿼리 직접 실행
     * 쿼리를 직접 실행할 때는 파라미터를 명시적으로 바인딩 해야 한다.
     *
     * @Param("itemName") 애노테이션을 사용하고, 애노테이션 값에 파라미터 이름을 주면 된다.
     */
    @Query("select i from Item i where i.itemName like :itemName and i.price <= :price")
    List<Item> findItems(@Param("itemName") String itemName, @Param("price") Integer price);
}
