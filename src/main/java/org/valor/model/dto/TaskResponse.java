package org.valor.model.dto;

import org.valor.model.enums.Priority;
import org.valor.model.enums.Status;

import java.time.LocalDateTime;

public record TaskResponse( 
    Long id,
    String title,
    String description,
    LocalDateTime dueDate,
    Status status, // TODO, IN_PROGRESS, DONE
    Priority priority,
    CategoryResponse category,
    Long categoryId,
    RecurrenceResponse recurrence
){}
