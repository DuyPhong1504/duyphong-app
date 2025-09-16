package com.duyphong.duyphong_app.mapper;

import com.duyphong.duyphong_app.dto.TaskDto;
import com.duyphong.duyphong_app.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    /**
     * Convert TaskEntity to TaskDto
     * @param entity the task entity to convert
     * @return the converted task DTO
     */
    TaskDto toDto(TaskEntity entity);

    /**
     * Convert TaskDto to TaskEntity
     * Note: id, createdAt, updatedAt, employee, dueDate, and status fields are handled separately
     * @param dto the task DTO to convert
     * @return the converted task entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "dueDate", ignore = true)
    @Mapping(target = "status", expression = "java(com.duyphong.duyphong_app.enumeration.TaskStatus.TO_DO)")
    TaskEntity toEntity(TaskDto dto);

    /**
     * Convert a list of TaskEntity to a list of TaskDto
     * @param entities the list of task entities to convert
     * @return the list of converted task DTOs
     */
    List<TaskDto> toDtoList(List<TaskEntity> entities);
}
