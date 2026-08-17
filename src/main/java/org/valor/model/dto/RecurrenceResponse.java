package org.valor.model.dto;

import org.valor.model.enums.RecurrenceType;

import java.time.LocalDate;

public record RecurrenceResponse (
    RecurrenceType type,   // DAILY, WEEKLY, MONTHLY
    Integer interval,     // шаг повторения
    LocalDate endDate     // дата окончания повторений (может быть null)
){}
