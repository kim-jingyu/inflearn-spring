package practice.core2.scope;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class JSR330ProviderTest {

    @Test
    void test() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class, PrototypeBean.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        int count1 = singletonBean1.logic();
        System.out.println("count1 = " + count1);

        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        int count2 = singletonBean2.logic();
        System.out.println("count2 = " + count2);
    }

    static class SingletonBean {
        @Autowired
        private Provider<PrototypeBean> provider;

        public int logic() {
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void close() {
            System.out.println("PrototypeBean.close " + this);
        }
    }
}
