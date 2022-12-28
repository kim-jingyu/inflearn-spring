package hello.core.lifecycle;

/**
 * NetworkClient는 애플리케이션 시작 시점에 connect()를 호출해서 연결을 맺어두어야 한다.
 * 애플리케이션이 종료되면 disconnect()를 호출해서 연결을 끊어야한다.
 */
public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call = " + url + ", message = " + message);
    }

    public void disconnect() {
        System.out.println("disconnect = " + url);
    }
}
