package org.valor.service.taskdiary;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.valor.model.dto.TaskDiaryDto;
import org.valor.model.dto.TaskDiaryUpdateRequest;

import java.util.List;
import java.util.UUID;

@Service
public interface TaskDiaryService {

    List<TaskDiaryDto> getAllTaskDiary(Pageable pageable);

    TaskDiaryDto getByTaskDiaryById(UUID id);

    UUID createProduct(TaskDiaryDto request);

    UUID updateTaskDiary(UUID id, TaskDiaryUpdateRequest request);

    UUID deleteTaskDiary(UUID id);
}
