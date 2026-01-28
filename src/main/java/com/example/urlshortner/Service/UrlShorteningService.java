package com.example.urlshortner.Service;

import com.example.urlshortner.Entity.UrlMapping;
import com.example.urlshortner.Repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlShorteningService {

    private final UrlMappingRepository urlMappingRepository;

    public UrlShorteningService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMapping createShortUrl(String originalUrl,
                                     String customAlias,
                                     LocalDateTime expiryTime) {

        String shortCode;

        // 1️⃣ Decide short code strategy (Hybrid)
        if (customAlias != null && !customAlias.isBlank()) {
            validateCustomAlias(customAlias);
            shortCode = customAlias;
        } else {
            shortCode = generateUniqueShortCode(originalUrl);
        }

        // 2️⃣ Create UrlMapping entity
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(shortCode);
        mapping.setCreatedAt(LocalDateTime.now());
        mapping.setExpiryTime(expiryTime);
        mapping.setClickCount(0L);
        mapping.setIsActive(true);

        // 3️⃣ Save to DB
        return urlMappingRepository.save(mapping);
    }

    // ---------------- HELPER METHODS ----------------

    private void validateCustomAlias(String alias) {
        if (urlMappingRepository.existsByShortCode(alias)) {
            throw new RuntimeException("Custom alias already exists");
        }
    }

    private String generateUniqueShortCode(String originalUrl) {
        String shortCode;

        do {
            shortCode = generateBase62Hash(originalUrl);
        } while (urlMappingRepository.existsByShortCode(shortCode));

        return shortCode;
    }

    private String generateBase62Hash(String input) {
        // simple but effective
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        long hash = Math.abs(input.hashCode() ^ System.nanoTime());

        StringBuilder sb = new StringBuilder();
        while (hash > 0) {
            sb.append(chars.charAt((int) (hash % 62)));
            hash /= 62;
        }

        return sb.toString();
    }
}

