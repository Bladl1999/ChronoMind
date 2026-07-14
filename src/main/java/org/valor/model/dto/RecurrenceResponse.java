package org.valor.model.dto;

import org.valor.model.enums.RecurrenceType;

import java.time.LocalDate;

public class RecurrenceResponse {
    private RecurrenceType type;   // DAILY, WEEKLY, MONTHLY
    private Integer interval;      // шаг повторения
    private LocalDate endDate;     // дата окончания повторений (может быть null)
}
