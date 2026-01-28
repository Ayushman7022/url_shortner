package com.example.urlshortner.Controller;

import com.example.urlshortner.DTO.AnalyticsResponseDto;
import com.example.urlshortner.Service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "http://localhost:63342") // Allow your frontend origin
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService){
        this.analyticsService=analyticsService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<AnalyticsResponseDto> getAnalytics(
            @PathVariable String shortCode
    ){
        AnalyticsResponseDto response= analyticsService.getAnalyticsForShortCode(shortCode);

        return ResponseEntity.ok(response);
    }
}
