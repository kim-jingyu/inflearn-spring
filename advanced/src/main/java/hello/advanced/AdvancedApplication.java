package hello.advanced;

import hello.advanced.ch0_1_2.trace.logtrace.LogTrace;
import hello.advanced.ch0_1_2.trace.logtrace.ThreadLocalLogTrace;
import hello.advanced.proxy.config.proxyfactory.ProxyFactoryConfigV2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(ProxyFactoryConfigV2.class)
@SpringBootApplication(scanBasePackages = "hello.advanced.proxy.app.componentscan")
public class AdvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedApplication.class, args);
	}

	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}
}
