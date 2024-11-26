package com.taskmanagement.dto;

import com.taskmanagement.model.SubTask;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubTaskResponse {
    private Long id;
    private int status;
    private Long taskId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SubTaskResponse fromSubTask(SubTask subTask) {
        SubTaskResponse response = new SubTaskResponse();
        response.setId(subTask.getId());
        response.setStatus(subTask.getStatus());
        response.setTaskId(subTask.getTask().getId());
        response.setCreatedAt(subTask.getCreatedAt());
        response.setUpdatedAt(subTask.getUpdatedAt());
        return response;
    }
}
