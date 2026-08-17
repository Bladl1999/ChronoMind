package org.valor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.valor.model.enums.RecurrenceType;

import java.time.LocalDate;

public record RecurrenceRequest (
        @JsonProperty(value = "type") RecurrenceType type,   // DAILY, WEEKLY, MONTHLY
        @JsonProperty(value = "interval") Integer interval,      // шаг повторения (например, каждые 2 дня)
        @JsonProperty(value = "endDate") LocalDate endDate   // дата окончания повторений (опционально)
){}
