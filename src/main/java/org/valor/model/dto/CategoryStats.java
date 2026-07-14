package org.valor.model.dto;

public class CategoryStats {
    private Long categoryId;
    private String categoryName;
    private int totalTimeMinutes;
    private int taskCount;
    private String color;          // опционально
    private double percentOfTotal; // для таблицы
}
