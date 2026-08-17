package org.valor.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.valor.model.dto.TaskDateUpdateRequest;
import org.valor.model.dto.TaskRequest;
import org.valor.model.dto.TaskResponse;
import org.valor.model.entity.Users;
import org.valor.model.enums.Priority;
import org.valor.model.enums.Status;
import org.valor.service.task.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//    @GetMapping
//    public List<TaskResponse> getTasks(Pageable pageable,
//                                       @RequestParam(required = false) String search,
//                                       @RequestParam(required = false) Status status,
//                                       @RequestParam(required = false) Priority priority,
//                                       @RequestParam(required = false) Long categoryId,
//                                       @RequestParam(required = false) LocalDate dueDateFrom,
//                                       @RequestParam(required = false) LocalDate dueDateTo) {
//        return taskService.findTasks(pageable, search, status, priority, categoryId, dueDateFrom, dueDateTo);
//    }

    @GetMapping
    public Page<TaskResponse> getTasks(Pageable pageable,
                                       @RequestParam(required = false) String search,
                                       @RequestParam(required = false) Status status,
                                       @RequestParam(required = false) Priority priority,
                                       @RequestParam(required = false) Long categoryId,
                                       @RequestParam(required = false) LocalDate dueDateFrom,
                                       @RequestParam(required = false) LocalDate dueDateTo) {
        return taskService.findTasks(pageable, search, status, priority, categoryId, dueDateFrom, dueDateTo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody TaskRequest request,
                                   @AuthenticationPrincipal Users user) {
        return taskService.createTask(request, user);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,
                                   @RequestBody TaskRequest request,
                                   @AuthenticationPrincipal User user) {
        return taskService.updateTask(id, request, user);
    }

    @PatchMapping("/{id}")
    public TaskResponse patchTask(@PathVariable Long id,
                                  @RequestBody Map<String, Object> updates,
                                  @AuthenticationPrincipal User user) {
        return taskService.patchTask(id, updates, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id,
                           @AuthenticationPrincipal User user) {
        taskService.deleteTask(id, user);
    }

    @GetMapping("/calendar")
    public List<TaskResponse> getTasksForCalendar(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Long categoryId) {
        return taskService.findTasksBetweenDates(start, end, status, priority, categoryId);
    }

    @GetMapping("/calendar/counts")
    public Map<String, Integer> getTaskCountsForCalendar(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Long categoryId) {
        return taskService.countTasksPerDay(start, end, status, priority, categoryId);
    }

    @PatchMapping("/{id}/date")
    public TaskResponse updateTaskDate(@PathVariable Long id,
                                       @RequestBody TaskDateUpdateRequest request,
                                       @AuthenticationPrincipal User user) {
        return taskService.updateTaskDate(id, request, user);
    }
}
