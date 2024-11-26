package com.taskmanagement.repository;

import com.taskmanagement.model.Task;
import com.taskmanagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByUserAndDeletedFalse(User user, Pageable pageable);
    
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.deleted = false " +
           "AND (:priority IS NULL OR t.priority = :priority) " +
           "AND (:dueDate IS NULL OR t.dueDate <= :dueDate)")
    Page<Task> findByUserWithFilters(
            @Param("user") User user,
            @Param("priority") Task.Priority priority,
            @Param("dueDate") LocalDateTime dueDate,
            Pageable pageable
    );
}
