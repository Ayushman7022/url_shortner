package com.example.urlshortner.Controller;

import com.example.urlshortner.Entity.UrlMapping;
import com.example.urlshortner.Service.UrlShorteningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/urls")
@CrossOrigin(origins = "http://localhost:63342") // Allow your frontend origin
public class UrlController {
    private final UrlShorteningService urlShorteningService;
    public UrlController( UrlShorteningService urlShorteningService){
        this.urlShorteningService=urlShorteningService;
    }

    @PostMapping
    public ResponseEntity<UrlMapping> createShortUrl(
            @RequestParam String originalurl,
            @RequestParam(required = false) String customAlias,
            @RequestParam(required = false) String expiry
    ) {
        System.out.println("🔥 createShortUrl HIT");
        LocalDateTime expiryTime = null;

        if (expiry != null && !expiry.isBlank()) {
            expiryTime = LocalDateTime.parse(expiry);
        }

        UrlMapping mapping =
                urlShorteningService.createShortUrl(originalurl, customAlias, expiryTime);

        return ResponseEntity.ok(mapping);
    }



}
