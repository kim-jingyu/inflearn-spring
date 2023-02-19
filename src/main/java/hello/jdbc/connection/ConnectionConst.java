package hello.jdbc.connection;

/**
 * 인스턴스 직접 생성을 막는 차원에서 추상 클래스로 만든다.
 */
public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
