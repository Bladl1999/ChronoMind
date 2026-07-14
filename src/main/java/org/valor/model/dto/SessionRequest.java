package org.valor.model.dto;

import org.valor.model.enums.SessionType;

import java.time.LocalDateTime;

public class SessionRequest {
    private Long taskId;
    private SessionType type;      // WORK, SHORT_BREAK, LONG_BREAK
    private Integer durationMinutes;
    private LocalDateTime completedAt; // опционально
}
