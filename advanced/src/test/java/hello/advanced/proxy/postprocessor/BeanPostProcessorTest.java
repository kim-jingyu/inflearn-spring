package hello.advanced.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {
    @Test
    void postProcessor() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        B b = context.getBean("beanA", B.class);
        System.out.println("b.call() = " + b.call());
    }

    @Configuration
    static class BeanConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        @Bean
        public AToBConvertBeanPostProcessor convertBeanPostProcessor() {
            return new AToBConvertBeanPostProcessor();
        }
    }

    static class A {
        public String call () {
            return "a";
        }
    }

    static class B {
        public String call() {
            return "b";
        }
    }

    @Slf4j
    static class AToBConvertBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("bean = {}, beanName = {}", bean, beanName);
            if (bean instanceof A) {
                return new B();
            }

            return bean;
        }
    }

}
