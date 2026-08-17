package org.valor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.valor.model.enums.Priority;

import java.time.LocalDateTime;

public record TaskRequest (
    @JsonProperty(value = "title") String title,
    @JsonProperty(value = "description") String description,
    @JsonProperty(value = "LocalDateTime") LocalDateTime dueDate,
    @JsonProperty(value = "priority") Priority priority, // enum LOW, MEDIUM, HIGH
    @JsonProperty(value = "categoryId") Long categoryId,
    @JsonProperty(value = "recurrence") RecurrenceRequest recurrence,
    @JsonProperty(value = "version") Long version // для PUT/PATCH
){}

