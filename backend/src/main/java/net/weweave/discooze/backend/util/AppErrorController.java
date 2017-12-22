package net.weweave.discooze.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class AppErrorController implements ErrorController {
    @Value("${proxy.public-base-path}")
    private String publicBasePath;

    @RequestMapping(value = "/error", produces = "text/html")
    public ResponseEntity<String> errorHtml() throws IOException {
        System.out.println("Not found --> replacing with index");
        File file = new File("./static/index.html");
        Path path = Paths.get(file.toURI());
        byte[] fileBytes = Files.readAllBytes(path);
        String content = new String(fileBytes);
        content = content.replace("[PUBLIC_BASE_PATH]", this.publicBasePath);
        return ResponseEntity.ok(content);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
