package hello.upload.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v1")
public class ServletUploadControllerV1 {

    @GetMapping("/upload")
    public String uploadForm() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String fileUpload(HttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request); // RequestFacade -> StandardMultipartHttpServletRequest

        String itemName = request.getParameter("itemName");
        log.info("itemName = {}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts = {}", parts);
        for (Part part : parts) {
            log.info("part.getContentType() = {}", part.getContentType());
            log.info("part.getName() = {}", part.getName());
        }

        return "upload-form";
    }
}
