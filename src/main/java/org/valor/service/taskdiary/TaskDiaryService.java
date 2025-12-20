package org.valor.service.taskdiary;

import org.springframework.stereotype.Service;
import org.valor.model.dto.TaskDiaryDto;

import java.util.List;
import java.util.UUID;

@Service
public interface TaskDiaryService {

    List<TaskDiaryDto> getAllTaskDiary();

    TaskDiaryDto getByTaskDiaryById(UUID id);

    UUID createProduct(TaskDiaryDto request);

    UUID updateTaskDiary(UUID id, TaskDiaryDto request);

    UUID deleteTaskDiary(UUID id);
}
