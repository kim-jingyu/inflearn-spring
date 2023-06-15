package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    public void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoType.class);

        ProtoType bean1 = ac.getBean(ProtoType.class);
        System.out.println("bean1 = " + bean1);

        ProtoType bean2 = ac.getBean(ProtoType.class);
        System.out.println("bean2 = " + bean2);

        Assertions.assertThat(bean1).isNotSameAs(bean2);

        ac.close();
        bean1.destroy();
        bean2.destroy();
    }

    @Scope("prototype")
    static class ProtoType {

        @PostConstruct
        public void init() {
            System.out.println("ProtoType.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("ProtoType.destroy");
        }
    }
}
