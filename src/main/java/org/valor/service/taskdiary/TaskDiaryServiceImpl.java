package org.valor.service.taskdiary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.valor.model.dto.TaskDiaryDto;
import org.valor.model.dto.TaskDiaryUpdateRequest;
import org.valor.model.entity.TaskDiary;
import org.valor.repository.TaskDiaryRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TaskDiaryServiceImpl implements TaskDiaryService {

    private final TaskDiaryRepository repository;

    @Autowired
    public TaskDiaryServiceImpl(TaskDiaryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TaskDiaryDto> getAllTaskDiary(Pageable pageable) {
        return repository.findAll(pageable)
                .map(TaskDiaryDto::fromEntity)
                .toList();
    }

    @Override
    public TaskDiaryDto getByTaskDiaryById(UUID id) {
        TaskDiary taskDiary =  repository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
        return TaskDiaryDto.fromEntity(taskDiary);
    }

    @Override
    public UUID createProduct(TaskDiaryDto request) {
        TaskDiary taskDiary = repository.save( new TaskDiary(request));
        return taskDiary.getId();
    }

    @Override
    public UUID updateTaskDiary(UUID id, TaskDiaryUpdateRequest request) {
        TaskDiary taskDiary = repository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
        TaskDiary update = taskDiary.update(request);
        return repository.save(update).getId();

    }

    @Override
    public UUID deleteTaskDiary(UUID id) {
        return repository.safeDelete(id, Instant.now());
    }
}
