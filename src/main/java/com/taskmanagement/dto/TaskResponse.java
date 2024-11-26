package com.taskmanagement.dto;

import com.taskmanagement.model.Task;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Task.Priority priority;
    private Task.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<SubTaskResponse> subTasks;

    public static TaskResponse fromTask(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setPriority(task.getPriority());
        response.setStatus(task.getStatus());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        
        if (task.getSubTasks() != null) {
            response.setSubTasks(task.getSubTasks().stream()
                .filter(subTask -> !subTask.isDeleted())
                .map(SubTaskResponse::fromSubTask)
                .collect(Collectors.toList()));
        }
        
        return response;
    }
}
