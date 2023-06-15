package practice.core2.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void test(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        BeanA beanA = ac.getBean(BeanA.class);
        System.out.println("beanA = " + beanA);

//        BeanB beanB = ac.getBean(BeanB.class);
//        System.out.println("beanB = " + beanB);
    }

    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeFilter.class),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeFilter.class)
    )
    static class ComponentFilterAppConfig{

    }
}
