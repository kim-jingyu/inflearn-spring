package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, Prototype.class);

        ClientBean bean1 = ac.getBean(ClientBean.class);
        int count1 = bean1.logic();
        System.out.println("count = " + count1);
        assertThat(count1).isEqualTo(1);

        ClientBean bean2 = ac.getBean(ClientBean.class);
        int count2 = bean2.logic();
        System.out.println("count2 = " + count2);
        assertThat(count2).isEqualTo(2);

        ac.close();
    }

    @Scope("singleton")
    static class ClientBean {
        private final Prototype prototype;

        @Autowired
        public ClientBean(Prototype prototype) {
            this.prototype = prototype;
        }

        public int logic() {
            prototype.addCount();
            return prototype.getCount();
        }

        @PostConstruct
        public void init() {
            System.out.println("Singleton.init");
        }

        @PreDestroy
        public void destroy() {
            prototype.destroy();
            System.out.println("Singleton.destroy");
        }
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
