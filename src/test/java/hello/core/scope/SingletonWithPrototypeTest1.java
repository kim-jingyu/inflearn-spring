package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Prototype.class);

        Prototype bean1 = ac.getBean(Prototype.class);
        bean1.addCount();
        int count1 = bean1.getCount();
        System.out.println("count1 = " + count1);

        Prototype bean2 = ac.getBean(Prototype.class);
        bean2.addCount();
        int count2 = bean2.getCount();
        System.out.println("count2 = " + count2);

        Assertions.assertThat(count1).isEqualTo(count2);
    }

    @Scope("singleton")
    static class Singleton {

    }

    @Scope("prototype")
    static class Prototype {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("Prototype.init , " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Prototype.destroy");
        }
    }
}
