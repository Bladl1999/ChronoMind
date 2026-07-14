package org.valor.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.valor.model.dto.CategoryStats;
import org.valor.model.dto.ProductivityPoint;
import org.valor.model.dto.StatisticsOverview;
import org.valor.service.statistics.StatisticsService;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/overview")
    public StatisticsOverview getOverview(@RequestParam(required = false) LocalDate from,
                                          @RequestParam(required = false) LocalDate to,
                                          @AuthenticationPrincipal User user) {
        return statisticsService.getOverview(from, to, user);
    }

    @GetMapping("/productivity")
    public List<ProductivityPoint> getProductivity(@RequestParam LocalDate from,
                                                   @RequestParam LocalDate to,
                                                   @RequestParam(defaultValue = "DAY") PeriodUnit groupBy,
                                                   @AuthenticationPrincipal User user) {
        return statisticsService.getProductivity(from, to, groupBy, user);
    }

    @GetMapping("/categories")
    public List<CategoryStats> getCategoryStats(@RequestParam(required = false) LocalDate from,
                                                @RequestParam(required = false) LocalDate to,
                                                @AuthenticationPrincipal User user) {
        return statisticsService.getCategoryStats(from, to, user);
    }

    @GetMapping("/top-categories")
    public List<CategoryStats> getTopCategories(@RequestParam(required = false) LocalDate from,
                                                @RequestParam(required = false) LocalDate to,
                                                @AuthenticationPrincipal User user) {
        return statisticsService.getTopCategories(from, to, user);
    }

    @GetMapping("/daily")
    public List<Object> getDailyStats(@RequestParam LocalDate from,
                                         @RequestParam LocalDate to,
                                         @AuthenticationPrincipal User user) {
        return statisticsService.getDailyStats(from, to, user);
    }

    @GetMapping("/export")
    public ResponseEntity<File> exportStatistics(@RequestParam(required = false) LocalDate from,
                                                 @RequestParam(required = false) LocalDate to,
                                                 @RequestParam(defaultValue = "csv") String format,
                                                 @AuthenticationPrincipal User user) {
        // Генерация CSV/JSON файла
        return null;
    }
}
