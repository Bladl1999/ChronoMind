package org.valor.service.statistics;

import org.apache.catalina.User;
import org.springframework.boot.convert.PeriodUnit;
import org.valor.model.dto.CategoryStats;
import org.valor.model.dto.ProductivityPoint;
import org.valor.model.dto.StatisticsOverview;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsService {
    StatisticsOverview getOverview(LocalDate from, LocalDate to, User user);

    List<ProductivityPoint> getProductivity(LocalDate from, LocalDate to, PeriodUnit groupBy, User user);

    List<CategoryStats> getCategoryStats(LocalDate from, LocalDate to, User user);

    List<CategoryStats> getTopCategories(LocalDate from, LocalDate to, User user);

    List<Object> getDailyStats(LocalDate from, LocalDate to, User user);
}
