package com.duyphong.duyphong_app.controller;

import com.duyphong.duyphong_app.dto.request.CreateTaskRequest;
import com.duyphong.duyphong_app.dto.response.TaskResponse;
import com.duyphong.duyphong_app.enumeration.TaskStatus;
import com.duyphong.duyphong_app.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
     * @param request the task creation request from request body (validated automatically)
     * @return ResponseEntity containing created TaskResponse with 201 status, 400 if validation fails
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        log.info("Received request to create new task: {} for employee: {}", 
                 request.getTaskName(), request.getEmployeeId());
        
        TaskResponse createdTask = taskService.createTask(request);
        
        String createdEmployeeId = createdTask.getEmployee() != null ? createdTask.getEmployee().getId() : null;
        log.info("Successfully created task with name: {} for employee: {}", 
                 createdTask.getTaskName(), createdEmployeeId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    /**
     * Get tasks with optional filtering by employee_id, status, and due_date
     * @param employeeId the employee ID to filter by (optional)
     * @param status the task status to filter by (optional)
     * @param dueDate the due date to filter by in format yyyy-MM-dd (optional)
     * @return ResponseEntity containing list of TaskResponse with 200 status
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(value = "employee_id", required = false) String employeeId,
            @RequestParam(value = "status", required = false) TaskStatus status,
            @RequestParam(value = "due_date", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        
        log.info("Received request to get tasks with filters - employee_id: {}, status: {}, due_date: {}", 
                 employeeId, status, dueDate);
        
        List<TaskResponse> tasks = taskService.getTasksWithFilters(employeeId, status, dueDate);
        
        log.info("Successfully retrieved {} tasks", tasks.size());
        
        return ResponseEntity.ok(tasks);
    }

}
