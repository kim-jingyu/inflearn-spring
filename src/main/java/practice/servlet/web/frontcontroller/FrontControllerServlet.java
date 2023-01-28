package practice.servlet.web.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import practice.servlet.web.frontcontroller.v3.MemberFormControllerV3;
import practice.servlet.web.frontcontroller.v3.MemberListControllerV3;
import practice.servlet.web.frontcontroller.v3.MemberSaveControllerV3;
import practice.servlet.web.frontcontroller.v4.MemberFormControllerV4;
import practice.servlet.web.frontcontroller.v4.MemberListControllerV4;
import practice.servlet.web.frontcontroller.v4.MemberSaveControllerV4;
import practice.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import practice.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import practice.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "frontControllerServlet", urlPatterns = "/front-controller/*")
public class FrontControllerServlet extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new ConcurrentHashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServlet() {
        initHandlerMappingMaps();
        initHandlerAdapters();
    }

    private void initHandlerMappingMaps() {
        handlerMappingMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(request, response, handler);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/view/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter 를 찾을 수 없습니다. handler = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }
}
