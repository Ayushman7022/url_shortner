package com.example.urlshortner.Repository;

import com.example.urlshortner.Entity.ClickAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<ClickAnalytics, Long> {

    @Query("""
        SELECT COUNT(c)
        FROM ClickAnalytics c
        WHERE c.shortCode = :shortCode
    """)
    long countByShortCode(@Param("shortCode") String shortCode);

    @Query("""
        SELECT DATE(c.clickedAt), COUNT(c)
        FROM ClickAnalytics c
        WHERE c.shortCode = :shortCode
        GROUP BY DATE(c.clickedAt)
        ORDER BY DATE(c.clickedAt)
    """)
    List<Object[]> findClicksPerDay(@Param("shortCode") String shortCode);

    @Query("""
        SELECT c.browser, COUNT(c)
        FROM ClickAnalytics c
        WHERE c.shortCode = :shortCode
        GROUP BY c.browser
    """)
    List<Object[]> findBrowserStats(@Param("shortCode") String shortCode);

    @Query("""
        SELECT c.os, COUNT(c)
        FROM ClickAnalytics c
        WHERE c.shortCode = :shortCode
        GROUP BY c.os
    """)
    List<Object[]> findOsStats(@Param("shortCode") String shortCode);

    @Query("""
        SELECT MAX(c.clickedAt)
        FROM ClickAnalytics c
        WHERE c.shortCode = :shortCode
    """)
    LocalDateTime findLastAccessedTime(@Param("shortCode") String shortCode);
}
