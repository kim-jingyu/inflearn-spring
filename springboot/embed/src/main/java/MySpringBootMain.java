import boot.MySpringApplication;
import boot.MySpringBootApplication;

@MySpringBootApplication
public class MySpringBootMain {
    public static void main(String[] args) {
        System.out.println("MySpringBootMain.main");

        MySpringApplication.run(MySpringBootMain.class, args);
    }
}
