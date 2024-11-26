package com.taskmanagement.repository;

import com.taskmanagement.model.SubTask;
import com.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    List<SubTask> findByTaskAndDeletedFalse(Task task);
    
    @Query("SELECT s FROM SubTask s WHERE s.deleted = false " +
           "AND (:taskId IS NULL OR s.task.id = :taskId)")
    List<SubTask> findByTaskIdWithFilter(@Param("taskId") Long taskId);
}
