package org.valor.service.taskdiary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.valor.model.dto.TaskDiaryDto;
import org.valor.repository.TaskDiaryRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TaskDiaryServiceImpl implements TaskDiaryService {

    private final TaskDiaryRepository repository;

    @Autowired
    public TaskDiaryServiceImpl(TaskDiaryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TaskDiaryDto> getAllTaskDiary() {
        return List.of();
    }

    @Override
    public TaskDiaryDto getByTaskDiaryById(UUID id) {
        return null;
    }

    @Override
    public UUID createProduct(TaskDiaryDto request) {
        return null;
    }

    @Override
    public UUID updateTaskDiary(UUID id, TaskDiaryDto request) {
        return null;
    }

    @Override
    public UUID deleteTaskDiary(UUID id) {
        return null;
    }
}
