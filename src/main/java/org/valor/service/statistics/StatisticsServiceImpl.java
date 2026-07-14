package org.valor.service.statistics;

import org.apache.catalina.User;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.stereotype.Service;
import org.valor.model.dto.CategoryStats;
import org.valor.model.dto.ProductivityPoint;
import org.valor.model.dto.StatisticsOverview;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService{

    @Override
    public StatisticsOverview getOverview(LocalDate from, LocalDate to, User user) {
        return null;
    }

    @Override
    public List<ProductivityPoint> getProductivity(LocalDate from, LocalDate to, PeriodUnit groupBy, User user) {
        return List.of();
    }

    @Override
    public List<CategoryStats> getCategoryStats(LocalDate from, LocalDate to, User user) {
        return List.of();
    }

    @Override
    public List<CategoryStats> getTopCategories(LocalDate from, LocalDate to, User user) {
        return List.of();
    }

    @Override
    public List<Object> getDailyStats(LocalDate from, LocalDate to, User user) {
        return List.of();
    }
}
