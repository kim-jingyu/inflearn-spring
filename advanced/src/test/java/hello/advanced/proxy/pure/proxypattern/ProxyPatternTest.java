package hello.advanced.proxy.pure.proxypattern;

import hello.advanced.proxy.pure.proxypattern.code.CacheProxy;
import hello.advanced.proxy.pure.proxypattern.code.ProxyPatternClient;
import hello.advanced.proxy.pure.proxypattern.code.RealSubject;
import hello.advanced.proxy.pure.proxypattern.code.Subject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {
    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        Subject realSubject = new RealSubject();
        Subject cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }
}
