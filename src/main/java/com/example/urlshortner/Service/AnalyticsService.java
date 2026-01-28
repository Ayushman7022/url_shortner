package com.example.urlshortner.Service;

import com.example.urlshortner.DTO.AnalyticsResponseDto;
import com.example.urlshortner.Repository.AnalyticsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public AnalyticsResponseDto getAnalyticsForShortCode(String shortCode) {

        long totalClicks =
                analyticsRepository.countByShortCode(shortCode);

        List<Object[]> clicksPerDayRaw =
                analyticsRepository.findClicksPerDay(shortCode);

        List<Object[]> browserStatsRaw =
                analyticsRepository.findBrowserStats(shortCode);

        List<Object[]> osStatsRaw =
                analyticsRepository.findOsStats(shortCode);

        LocalDateTime lastAccessed =
                analyticsRepository.findLastAccessedTime(shortCode);

        return new AnalyticsResponseDto(
                totalClicks,
                mapClicksPerDay(clicksPerDayRaw),
                mapStats(browserStatsRaw),
                mapStats(osStatsRaw),
                lastAccessed
        );

    }

    private Map<String, Long> mapStats(List<Object[]> rawData) {
        Map<String, Long> result = new HashMap<>();

        for (Object[] row : rawData) {
            String key = (String) row[0];
            Long count = (Long) row[1];
            result.put(key, count);
        }

        return result;
    }

    private Map<LocalDate, Long> mapClicksPerDay(List<Object[]> rawData) {
        Map<LocalDate, Long> result = new java.util.TreeMap<>();

        for (Object[] row : rawData) {
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            Long count = (Long) row[1];
            result.put(date, count);
        }

        return result;
    }
}
