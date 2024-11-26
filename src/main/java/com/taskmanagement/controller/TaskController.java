package com.taskmanagement.controller;

import com.taskmanagement.dto.TaskRequest;
import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.model.Task;
import com.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Task task = taskService.createTask(request, userDetails.getUsername());
        return ResponseEntity.ok(TaskResponse.fromTask(task));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getUserTasks(
            @RequestParam(required = false) Task.Priority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate,
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        Page<Task> tasks = taskService.getUserTasks(userDetails.getUsername(), priority, dueDate, pageable);
        Page<TaskResponse> responses = new PageImpl<>(
            tasks.getContent().stream()
                .map(TaskResponse::fromTask)
                .collect(Collectors.toList()),
            pageable,
            tasks.getTotalElements()
        );
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate,
            @RequestParam(required = false) Task.Status status,
            @AuthenticationPrincipal UserDetails userDetails) {
        Task task = taskService.updateTask(taskId, dueDate, status, userDetails.getUsername());
        return ResponseEntity.ok(TaskResponse.fromTask(task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @AuthenticationPrincipal UserDetails userDetails) {
        taskService.deleteTask(taskId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
