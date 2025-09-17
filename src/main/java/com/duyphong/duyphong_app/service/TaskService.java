package com.duyphong.duyphong_app.service;

import com.duyphong.duyphong_app.dto.request.CreateTaskRequest;
import com.duyphong.duyphong_app.dto.response.TaskResponse;
import com.duyphong.duyphong_app.entity.TaskEntity;
import com.duyphong.duyphong_app.enumeration.TaskStatus;
import com.duyphong.duyphong_app.mapper.TaskMapper;
import com.duyphong.duyphong_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for Task operations
 * Contains business logic for task management
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    /**
     * Create a new task
     * @param request the task creation request
     * @return the created task as response DTO
     */
    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        log.info("Creating new task: {} for employee: {}", request.getTaskName(), request.getEmployeeId());
        
        // Convert request to entity
        TaskEntity taskEntity = taskMapper.toEntity(request);
        
        // Save the entity
        TaskEntity savedTask = taskRepository.save(taskEntity);
        
        log.info("Task created successfully with ID: {}", savedTask.getId());
        
        // Convert back to response DTO and return
        return taskMapper.toResponse(savedTask);
    }

    /**
     * Get tasks with optional filtering by employee_id, status, and due_date
     * @param employeeId the employee ID to filter by (optional)
     * @param status the task status to filter by (optional)
     * @param dueDate the due date to filter by (optional)
     * @return List of tasks matching the criteria
     * @throws IllegalArgumentException if all filter parameters are null
     */
    public List<TaskResponse> getTasksWithFilters(String employeeId, TaskStatus status, LocalDate dueDate) {
        log.info("Retrieving tasks with filters - employeeId: {}, status: {}, dueDate: {}", 
                 employeeId, status, dueDate);
        
        // Validation: At least one filter parameter must be provided
        if (employeeId == null && status == null && dueDate == null) {
            log.warn("No filter parameters provided - this could return too many results");
            throw new IllegalArgumentException("At least one filter parameter (employee_id, status, or due_date) must be provided");
        }
        
        List<TaskEntity> tasks = taskRepository.findTasksWithFilters(employeeId, status, dueDate);
        
        log.info("Found {} tasks matching the criteria", tasks.size());
        
        return taskMapper.toResponseList(tasks);
    }

}
