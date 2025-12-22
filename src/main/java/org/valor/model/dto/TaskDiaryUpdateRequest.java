package org.valor.model.dto;

import org.valor.enums.PriorityTaskEnum;

import java.time.Instant;
import java.util.Optional;

public record TaskDiaryUpdateRequest(
        Optional<String> name,
        Optional<String> taskNote,
        Optional<PriorityTaskEnum> priority,
        Optional<Instant> starTask,
        Optional<Instant> finishTask
) {
}
