package com.taskmanagement.controller;

import com.taskmanagement.dto.SubTaskResponse;
import com.taskmanagement.model.SubTask;
import com.taskmanagement.service.SubTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subtasks")
@RequiredArgsConstructor
public class SubTaskController {

    private final SubTaskService subTaskService;

    @PostMapping
    public ResponseEntity<SubTaskResponse> createSubTask(
            @RequestParam Long taskId,
            @AuthenticationPrincipal UserDetails userDetails) {
        SubTask subTask = subTaskService.createSubTask(taskId, userDetails.getUsername());
        return ResponseEntity.ok(SubTaskResponse.fromSubTask(subTask));
    }

    @GetMapping
    public ResponseEntity<List<SubTaskResponse>> getUserSubTasks(
            @RequestParam(required = false) Long taskId,
            @AuthenticationPrincipal UserDetails userDetails) {
        List<SubTask> subTasks = subTaskService.getUserSubTasks(taskId, userDetails.getUsername());
        List<SubTaskResponse> responses = subTasks.stream()
            .map(SubTaskResponse::fromSubTask)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{subTaskId}")
    public ResponseEntity<SubTaskResponse> updateSubTaskStatus(
            @PathVariable Long subTaskId,
            @RequestParam int status,
            @AuthenticationPrincipal UserDetails userDetails) {
        SubTask subTask = subTaskService.updateSubTaskStatus(subTaskId, status, userDetails.getUsername());
        return ResponseEntity.ok(SubTaskResponse.fromSubTask(subTask));
    }

    @DeleteMapping("/{subTaskId}")
    public ResponseEntity<Void> deleteSubTask(
            @PathVariable Long subTaskId,
            @AuthenticationPrincipal UserDetails userDetails) {
        subTaskService.deleteSubTask(subTaskId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
