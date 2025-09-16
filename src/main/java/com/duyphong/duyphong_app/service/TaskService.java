package com.duyphong.duyphong_app.service;

import com.duyphong.duyphong_app.dto.TaskDto;
import com.duyphong.duyphong_app.entity.TaskEntity;
import com.duyphong.duyphong_app.mapper.TaskMapper;
import com.duyphong.duyphong_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param taskDto the task data to create
     * @return the created task as DTO
     */
    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        log.info("Creating new task: {} for employee: {}", taskDto.getTaskName(), taskDto.getEmployeeId());
        
        // Convert DTO to entity
        TaskEntity taskEntity = taskMapper.toEntity(taskDto);
        
        // Save the entity
        TaskEntity savedTask = taskRepository.save(taskEntity);
        
        log.info("Task created successfully with ID: {}", savedTask.getId());
        
        // Convert back to DTO and return
        return taskMapper.toDto(savedTask);
    }

}
