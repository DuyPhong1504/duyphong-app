package com.duyphong.duyphong_app.controller;

import com.duyphong.duyphong_app.dto.TaskDto;
import com.duyphong.duyphong_app.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Task operations
 * Handles HTTP requests related to task management
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TaskController {

    private final TaskService taskService;

    /**
     * Create a new task
     * @param taskDto the task data from request body (validated automatically)
     * @return ResponseEntity containing created TaskDto with 201 status, 400 if validation fails
     */
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        log.info("Received request to create new task: {} for employee: {}", 
                 taskDto.getTaskName(), taskDto.getEmployeeId());
        
        TaskDto createdTask = taskService.createTask(taskDto);
        
        log.info("Successfully created task with name: {} for employee: {}", 
                 createdTask.getTaskName(), createdTask.getEmployeeId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

}
