package org.valor.service.task;

import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.valor.model.dto.TaskDateUpdateRequest;
import org.valor.model.dto.TaskRequest;
import org.valor.model.dto.TaskResponse;
import org.valor.model.entity.Task;
import org.valor.model.entity.Users;
import org.valor.model.enums.Priority;
import org.valor.model.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TaskService {
//    List<TaskResponse> findTasks(Pageable pageable, String search, Status status, Priority priority, Long categoryId, LocalDate dueDateFrom, LocalDate dueDateTo);

    Page<TaskResponse> findTasks(Pageable pageable, String search, Status status, Priority priority, Long categoryId, LocalDate dueDateFrom, LocalDate dueDateTo);

    TaskResponse createTask(TaskRequest request, Users user);

    TaskResponse updateTask(Long id, TaskRequest request, User user);

    TaskResponse patchTask(Long id, Map<String, Object> updates, User user);

    void deleteTask(Long id, User user);

    List<TaskResponse> findTasksBetweenDates(LocalDate start, LocalDate end, Status status, Priority priority, Long categoryId);

    Map<String, Integer> countTasksPerDay(LocalDate start, LocalDate end, Status status, Priority priority, Long categoryId);

    TaskResponse updateTaskDate(Long id, TaskDateUpdateRequest request, User user);

}
