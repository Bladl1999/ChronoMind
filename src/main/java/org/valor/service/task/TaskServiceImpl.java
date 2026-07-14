package org.valor.service.task;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.valor.model.dto.TaskDateUpdateRequest;
import org.valor.model.dto.TaskRequest;
import org.valor.model.dto.TaskResponse;
import org.valor.model.entity.Task;
import org.valor.model.enums.Priority;
import org.valor.model.enums.Status;
import org.valor.repository.TaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Page<TaskResponse> findTasks(Pageable pageable, String search, Status status, Priority priority, Long categoryId, LocalDate dueDateFrom, LocalDate dueDateTo) {
        return null;
    }

    @Override
    public TaskResponse createTask(TaskRequest request, User user) {
        return null;
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request, User user) {
        return null;
    }

    @Override
    public TaskResponse patchTask(Long id, Map<String, Object> updates, User user) {
        return null;
    }

    @Override
    public void deleteTask(Long id, User user) {

    }

    @Override
    public List<TaskResponse> findTasksBetweenDates(LocalDate start, LocalDate end, Status status, Priority priority, Long categoryId) {
        return List.of();
    }

    @Override
    public Map<String, Integer> countTasksPerDay(LocalDate start, LocalDate end, Status status, Priority priority, Long categoryId) {
        return Map.of();
    }

    @Override
    public TaskResponse updateTaskDate(Long id, TaskDateUpdateRequest request, User user) {
        return null;
    }

    public List<TaskResponse> findTasksBetweenDates(LocalDate start, LocalDate end) {
        // Преобразуем LocalDate в LocalDateTime (начало дня и конец дня) для корректного сравнения
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);
        // Вызываем репозиторий
//        List<Task> tasks = taskRepository.findByUserAndDueDateBetween(user, startDateTime, endDateTime);
        // ... фильтрация по остальным параметрам
//        return tasks.stream().map(mapper::toResponse).collect(Collectors.toList());
        return null;
    }

}
