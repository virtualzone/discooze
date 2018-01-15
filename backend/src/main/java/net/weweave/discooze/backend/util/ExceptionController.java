package net.weweave.discooze.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ControllerAdvice
@RestControllerAdvice
public class ExceptionController {
    @Value("${proxy.public-base-path}")
    private String publicBasePath;

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> notFound(HttpServletRequest request) throws IOException {
        String requestUri = request.getRequestURI().substring(request.getContextPath().length());
        if (!requestUri.startsWith("/api/")) {
            System.out.println("Not found: " + requestUri + " --> replacing with index");
            File file = new File("./static/index.html");
            Path path = Paths.get(file.toURI());
            byte[] fileBytes = Files.readAllBytes(path);
            String content = new String(fileBytes);
            content = content.replace("[PUBLIC_BASE_PATH]", this.publicBasePath);
            return ResponseEntity.ok(content);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> exception(Exception e) {
        System.out.println("Handling exception " + e.getMessage());
        return ResponseEntity.status(500).body("");
    }
}
