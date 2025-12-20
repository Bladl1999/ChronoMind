package org.valor.model.dto;

import org.valor.enums.PriorityTaskEnum;

import java.time.Instant;
import java.util.UUID;

public record TaskDiaryDto(
    UUID id,
    String name,
    String taskNote,
    PriorityTaskEnum priority,
    Instant starTask,
    Instant finishTask
) {

}
