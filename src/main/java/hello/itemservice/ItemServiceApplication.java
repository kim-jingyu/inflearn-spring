package hello.itemservice;

import hello.itemservice.config.*;
import hello.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

	/**
	 * @Profile("test")
	 * 프로필이 test 인 경우에만 데이터소스를 스프링 빈으로 등록한다. 즉, 테스트케이스에만 이 데이터소스를 스프링 빈으로 등록해서 사용하겠다는 뜻이다.
	 *
	 * jdbc:h2:mem:db
	 * 이 부분이 중요하다. 데이터소스를 만들때 이렇게만 적으면 Embedded Mode ( Memory Mode ) 로 동작하는 H2 Database 를 사용할 수 있다.
	 *
	 * DB_CLOSE_DELAY = -1
	 * Embedded Mode 에서는 Database Connection 이 모두 끊어지면 데이터베이스도 종료되는데, 그 것을 방지하는 설정이다.
	 *
	 * 이 데이터소스를 사용하면 Memory DB 를 사용할 수 있다.
	 */
//	@Bean
//	@Profile("test")
//	public DataSource dataSource() {
//		log.info("메모리 데이터베이스 초기화");
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//		return dataSource;
//	}
}
