package com.duyphong.duyphong_app.mapper;

import com.duyphong.duyphong_app.dto.request.CreateTaskRequest;
import com.duyphong.duyphong_app.dto.response.TaskResponse;
import com.duyphong.duyphong_app.entity.TaskEntity;
import com.duyphong.duyphong_app.enumeration.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    /**
     * Convert TaskEntity to TaskResponse with employee information
     * @param entity the task entity to convert
     * @return the converted task response DTO
     */
    public TaskResponse toResponse(TaskEntity entity) {
        if (entity == null) {
            return null;
        }

        return TaskResponse.builder()
                .id(entity.getId())
                .taskName(entity.getTaskName())
                .description(entity.getDescription())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .employee(entity.getEmployee()) // Direct assignment of EmployeeEntity
                .build();
    }

    /**
     * Convert CreateTaskRequest to TaskEntity (for creation)
     * @param request the task creation request to convert
     * @return the converted task entity
     */
    public TaskEntity toEntity(CreateTaskRequest request) {
        if (request == null) {
            return null;
        }

        return TaskEntity.builder()
                .employeeId(request.getEmployeeId())
                .taskName(request.getTaskName())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(TaskStatus.TO_DO) // Default status for new tasks
                .build();
    }

    /**
     * Convert a list of TaskEntity to a list of TaskResponse
     * @param entities the list of task entities to convert
     * @return the list of converted task response DTOs
     */
    public List<TaskResponse> toResponseList(List<TaskEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
