package org.valor.model.dto;

import org.valor.enums.PriorityTaskEnum;
import org.valor.model.entity.TaskDiary;

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
    public static TaskDiaryDto fromEntity(TaskDiary taskDiary) {
        return new TaskDiaryDto(
              taskDiary.getId(),
                taskDiary.getName(),
                taskDiary.getTaskNote(),
                PriorityTaskEnum.getPriorityTask(taskDiary.getPriority()),
                taskDiary.getStarTask(),
                taskDiary.getFinishTask()
        );
    }
}
