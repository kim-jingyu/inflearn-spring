package practice.servlet.web.frontcontroller.v3;

import practice.servlet.web.frontcontroller.ModelView;

import java.util.Map;

/**
 * version 3
 * 회원 등록 폼
 */
public class MemberFormControllerV3 implements ControllerV3{
    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
