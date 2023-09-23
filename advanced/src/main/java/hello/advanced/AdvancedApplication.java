package hello.advanced;

import hello.advanced.ch0_1_2.trace.logtrace.LogTrace;
import hello.advanced.ch0_1_2.trace.logtrace.ThreadLocalLogTrace;
import hello.advanced.proxy.config.dynamicproxy.DynamicProxyFilterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(DynamicProxyFilterConfig.class)
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
