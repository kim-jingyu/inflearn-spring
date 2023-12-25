package container;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import servlet.HelloServlet;

public class AppInitV1Servlet implements AppInit{
    @Override
    public void onStartUp(ServletContext servletContext) {
        System.out.println("AppInitV1Servlet.onStartUp");

        // 순수 서블릿 코드 등록 (프로그래밍 방식)
        ServletRegistration.Dynamic helloServlet = servletContext.addServlet("helloServlet", new HelloServlet());
        helloServlet.addMapping("/hello-servlet");
    }
}
