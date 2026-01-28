package com.example.urlshortner.Service;

import com.example.urlshortner.Entity.ClickAnalytics;
import com.example.urlshortner.Entity.UrlMapping;
import com.example.urlshortner.Repository.AnalyticsRepository;
import com.example.urlshortner.Repository.UrlMappingRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class RedirectService {
    private final UrlMappingRepository urlMappingRepository;
    private final AnalyticsRepository analyticsRepository;

    public RedirectService(UrlMappingRepository urlMappingRepository,
                           AnalyticsRepository analyticsRepository){
        this.urlMappingRepository= urlMappingRepository;
        this.analyticsRepository=analyticsRepository;
    }

    public String resolveShortCode(String shortCode , HttpServletRequest request){
        UrlMapping mapping = urlMappingRepository.findByShortCode(shortCode).
                orElseThrow(()-> new RuntimeException("Short URL not found"));

        if(!mapping.getIsActive()){
            throw new  RuntimeException("Short URL is disabled");
        }

        if(mapping.getExpiryTime()!=null && mapping.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Short URL has expired");
        }

        mapping.setClickCount(mapping.getClickCount()+1);
        urlMappingRepository.save(mapping);

        logAnalytics( shortCode , request);
        return mapping.getOriginalUrl();

    }

    private void logAnalytics(String shortCode,HttpServletRequest request){
        ClickAnalytics analytics = new ClickAnalytics();

        analytics.setShortCode(shortCode);
        analytics.setClickedAt(LocalDateTime.now());
        analytics.setIpAddress(request.getRemoteAddr());

        String userAgent=request.getHeader("User-Agent");
        analytics.setBrowser(extractBrowser(userAgent));
        analytics.setOs(extractOs(userAgent));
        analyticsRepository.save(analytics);

    }

    private String extractBrowser(String userAgent){
        if(userAgent == null) return "Unkown";
        if(userAgent.contains("Chrome")) return "Chrome";
        if(userAgent.contains("Firefox")) return "Firefox";
        if(userAgent.contains("Edge")) return "Edge";
        return "other";
    }

    private String extractOs(String userAgent){
        if(userAgent==null) return "Unknown";
        if(userAgent.contains("Windows")) return "Windows";
        if(userAgent.contains("Android")) return "Android";
        if(userAgent.contains("Mac")) return "MacOS";
        if(userAgent.contains("Linux")) return "Linux";

        return "Other";
    }
}
