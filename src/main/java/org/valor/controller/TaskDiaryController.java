package org.valor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.valor.model.dto.TaskDiaryDto;
import org.valor.model.dto.TaskDiaryUpdateRequest;
import org.valor.service.taskdiary.TaskDiaryService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task-diary")
public class TaskDiaryController {

    private final TaskDiaryService service;

    @Autowired
    public TaskDiaryController(TaskDiaryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TaskDiaryDto>> getAllTaskDiary( Pageable pageable){
        List<TaskDiaryDto> taskDiary = service.getAllTaskDiary(null, pageable);
        return ResponseEntity.ok(taskDiary);
    }


    @GetMapping("/timeline")
    public ResponseEntity<List<TaskDiaryDto>> timeLine( Pageable pageable) {
        List<TaskDiaryDto> taskDiary = service.getAllTaskDiary(null, pageable);
        return ResponseEntity.ok(taskDiary);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskDiaryDto>> search( @RequestParam("q") String query) {
        List<TaskDiaryDto> taskDiary = service.search(query);
        return ResponseEntity.ok(null);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskDiaryDto> getByTaskDiaryById(@PathVariable UUID id){
        TaskDiaryDto taskDiaryDto = service.getByTaskDiaryById(id);
        return ResponseEntity.ok(taskDiaryDto);
    }

    @PostMapping
    public ResponseEntity<UUID> createTaskDiary(@RequestBody TaskDiaryDto request) {
        UUID id = service.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateTaskDiary(@PathVariable UUID id, @RequestBody TaskDiaryUpdateRequest request) {
        UUID getId = service.updateTaskDiary(id, request);
        return ResponseEntity.ok(getId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteTaskDiary(@PathVariable UUID id){
        UUID getId = service.deleteTaskDiary(id);
        return ResponseEntity.ok(getId);
    }


}
