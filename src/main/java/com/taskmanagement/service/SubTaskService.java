package com.taskmanagement.service;

import com.taskmanagement.exception.ApiException;
import com.taskmanagement.model.SubTask;
import com.taskmanagement.model.Task;
import com.taskmanagement.repository.SubTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubTaskService {

    private final SubTaskRepository subTaskRepository;
    private final TaskService taskService;

    @Transactional
    public SubTask createSubTask(Long taskId, String username) {
        Task task = taskService.getTaskForUser(taskId, username);
        
        SubTask subTask = new SubTask();
        subTask.setTask(task);
        return subTaskRepository.save(subTask);
    }

    public List<SubTask> getUserSubTasks(Long taskId, String username) {
        if (taskId != null) {
            taskService.getTaskForUser(taskId, username); // Verify access
            return subTaskRepository.findByTaskIdWithFilter(taskId);
        }
        return subTaskRepository.findByTaskIdWithFilter(null);
    }

    @Transactional
    public SubTask updateSubTaskStatus(Long subTaskId, int status, String username) {
        SubTask subTask = getSubTaskForUser(subTaskId, username);
        
        if (status != 0 && status != 1) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Status must be 0 or 1");
        }
        
        subTask.setStatus(status);
        return subTaskRepository.save(subTask);
    }

    @Transactional
    public void deleteSubTask(Long subTaskId, String username) {
        SubTask subTask = getSubTaskForUser(subTaskId, username);
        subTask.setDeleted(true);
        subTaskRepository.save(subTask);
    }

    private SubTask getSubTaskForUser(Long subTaskId, String username) {
        SubTask subTask = subTaskRepository.findById(subTaskId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "SubTask not found"));
                
        // Verify user has access to parent task
        taskService.getTaskForUser(subTask.getTask().getId(), username);
        
        if (subTask.isDeleted()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "SubTask not found");
        }
        
        return subTask;
    }
}
