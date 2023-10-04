package container;

import jakarta.servlet.ServletContext;
import servlet.HelloServlet;

public class AppInitV1Servlet implements AppInit{
    @Override
    public void onStartUp(ServletContext servletContext) {
        System.out.println("AppInitV1Servlet.onStartUp");

        servletContext.addServlet("helloServlet", new HelloServlet())
                .addMapping("/hello-servlet");
    }
}
