package com.example.urlshortner.Controller;

import com.example.urlshortner.DTO.AnalyticsResponseDto;
import com.example.urlshortner.Service.RedirectService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

@Controller
@CrossOrigin(origins = "http://localhost:63342") // Allow your frontend origin
public class RedirectController {
    private final RedirectService redirectService;

    public RedirectController(RedirectService redirectService){
        this.redirectService=redirectService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode, HttpServletRequest request){
    String originalUrl = redirectService.resolveShortCode(shortCode,request);
    return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(originalUrl))
            .build();
    }
}
