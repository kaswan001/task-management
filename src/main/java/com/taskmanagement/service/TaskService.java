package com.taskmanagement.service;

import com.taskmanagement.dto.TaskRequest;
import com.taskmanagement.dto.TaskResponse;
import com.taskmanagement.exception.ApiException;
import com.taskmanagement.model.Task;
import com.taskmanagement.model.User;
import com.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Transactional
    public Task createTask(TaskRequest taskRequest, String username) {
        User user = userService.getCurrentUser(username);
        
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        task.setPriority(taskRequest.getPriority());
        task.setUser(user);
        
        return taskRepository.save(task);
    }

    public Page<Task> getUserTasks(String username, Task.Priority priority, LocalDateTime dueDate, Pageable pageable) {
        User user = userService.getCurrentUser(username);
        return taskRepository.findByUserWithFilters(user, priority, dueDate, pageable);
    }

    @Transactional
    public Task updateTask(Long taskId, LocalDateTime dueDate, Task.Status status, String username) {
        Task task = getTaskForUser(taskId, username);
        
        if (dueDate != null) {
            task.setDueDate(dueDate);
        }
        if (status != null) {
            task.setStatus(status);
        }
        
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long taskId, String username) {
        Task task = getTaskForUser(taskId, username);
        task.setDeleted(true);
        taskRepository.save(task);
    }

    public Task getTaskForUser(Long taskId, String username) {
        User user = userService.getCurrentUser(username);
        return taskRepository.findById(taskId)
                .filter(task -> task.getUser().getId().equals(user.getId()) && !task.isDeleted())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Task not found or access denied"));
    }
}
