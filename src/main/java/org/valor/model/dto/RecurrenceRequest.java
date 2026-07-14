package org.valor.model.dto;

import org.valor.model.enums.RecurrenceType;

import java.time.LocalDate;

public class RecurrenceRequest {
    private RecurrenceType type;   // DAILY, WEEKLY, MONTHLY
    private Integer interval;      // шаг повторения (например, каждые 2 дня)
    private LocalDate endDate;     // дата окончания повторений (опционально)
}
