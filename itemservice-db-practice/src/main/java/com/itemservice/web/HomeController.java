package com.itemservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 홈으로 요청이 왔을 때, /items 로 이동하는 컨트롤러
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "redirect:/items";
    }
}
