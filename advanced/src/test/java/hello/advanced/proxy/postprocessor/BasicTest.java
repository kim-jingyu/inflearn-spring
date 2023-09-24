package hello.advanced.proxy.postprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BasicTest {
    @Test
    void basic() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class);

        A a = context.getBean("beanA", A.class);
        a.call();

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(B.class));
    }
}

@Configuration
class BasicConfig{
    @Bean(name = "beanA")
    public A a() {
        return new A();
    }
}

class A {
    public String call() {
        return "a";
    }
}

class B {
    public String call() {
        return "b";
    }
}