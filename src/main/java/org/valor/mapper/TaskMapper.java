package org.valor.mapper;


import org.valor.model.dto.CategoryResponse;
import org.valor.model.dto.RecurrenceResponse;
import org.valor.model.dto.TaskRequest;
import org.valor.model.dto.TaskResponse;
import org.valor.model.entity.Category;
import org.valor.model.entity.Recurrence;
import org.valor.model.entity.Task;
import org.valor.model.entity.Users;
import org.valor.model.enums.Status;

public class TaskMapper {

    // Создание новой задачи
    public static Task toEntity(TaskRequest request, Users user, Category category) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setPriority(request.priority());
        task.setUser(user);
        task.setCategory(category);
        // Статус по умолчанию – TODO (или можно оставить null, если бизнес-логика диктует иное)
        task.setStatus(Status.TODO);
        // Обработка повторения
        if (request.recurrence() != null) {
            task.setRecurrence(RecurrenceMapper.toEntity(request.recurrence()));
        }
        // version не трогаем – при создании оно null, Hibernate сам подставит
        return task;
    }

    // Обновление существующей задачи
    public static void updateEntity(Task task, TaskRequest request, Category category) {
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setPriority(request.priority());
        task.setCategory(category);
        // Обновление повторения
        if (request.recurrence() != null) {
            if (task.getRecurrence() == null) {
                task.setRecurrence(new Recurrence());
            }
            RecurrenceMapper.updateEntity(task.getRecurrence(), request.recurrence());
        } else {
            // Если запрос явно удаляет повторение
            task.setRecurrence(null);
        }
        // Контроль версии: если клиент передал version, устанавливаем (Hibernate сравнит при сохранении)
        if (request.version() != null) {
            task.setVersion(request.version());
        }
        // Статус обычно меняется отдельными эндпоинтами, поэтому здесь не трогаем
    }

    public static TaskResponse toDto(Task task) {

        RecurrenceResponse recurrenceResponse = null;
        if(task.getRecurrence() != null) {
            recurrenceResponse = RecurrenceMapper.toDto(task.getRecurrence());
        }
        CategoryResponse categoryResponse = null;
        Long categoryId = null;
        if(task.getCategory() != null) {
            categoryResponse = CategoryMapper.toDto(task.getCategory());
            categoryId = task.getCategory().getId();
        }

        TaskResponse taskResponse = new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority(),
                categoryResponse,
                categoryId,
                recurrenceResponse
        );
        return taskResponse;
    }
}

