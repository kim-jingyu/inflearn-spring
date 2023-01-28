package practice.servlet.web.frontcontroller;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class ModelView {

    private String viewName;
    private Map<String, Object> model = new ConcurrentHashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
