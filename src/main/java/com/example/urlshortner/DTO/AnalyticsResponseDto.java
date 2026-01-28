package com.example.urlshortner.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class AnalyticsResponseDto {

    private long totalClicks;
    private Map<LocalDate, Long> clicksPerDay;
    private Map<String, Long> browserStats;
    private Map<String, Long> osStats;
    private LocalDateTime lastAccessed;

    // ✅ Constructor
    public AnalyticsResponseDto(long totalClicks,
                                Map<LocalDate, Long> clicksPerDay,
                                Map<String, Long> browserStats,
                                Map<String, Long> osStats,
                                LocalDateTime lastAccessed) {
        this.totalClicks = totalClicks;
        this.clicksPerDay = clicksPerDay;
        this.browserStats = browserStats;
        this.osStats = osStats;
        this.lastAccessed = lastAccessed;
    }

    // ✅ Getters
    public long getTotalClicks() {
        return totalClicks;
    }

    public Map<LocalDate, Long> getClicksPerDay() {
        return clicksPerDay;
    }

    public Map<String, Long> getBrowserStats() {
        return browserStats;
    }

    public Map<String, Long> getOsStats() {
        return osStats;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }
}
