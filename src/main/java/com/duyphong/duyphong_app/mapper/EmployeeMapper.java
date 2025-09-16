package com.duyphong.duyphong_app.mapper;

import com.duyphong.duyphong_app.dto.EmployeeDto;
import com.duyphong.duyphong_app.dto.EmployeeUpdateDto;
import com.duyphong.duyphong_app.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /**
     * Convert EmployeeEntity to EmployeeDto
     * @param entity the employee entity to convert
     * @return the converted employee DTO
     */
    EmployeeDto toDto(EmployeeEntity entity);

    /**
     * Convert EmployeeDto to EmployeeEntity
     * @param dto the employee DTO to convert
     * @return the converted employee entity
     */
    EmployeeEntity toEntity(EmployeeDto dto);

    /**
     * Convert a list of EmployeeEntity to a list of EmployeeDto
     * @param entities the list of employee entities to convert
     * @return the list of converted employee DTOs
     */
    List<EmployeeDto> toDtoList(List<EmployeeEntity> entities);

    /**
     * Convert a list of EmployeeDto to a list of EmployeeEntity
     * @param dtos the list of employee DTOs to convert
     * @return the list of converted employee entities
     */
    List<EmployeeEntity> toEntityList(List<EmployeeDto> dtos);

    /**
     * Update an existing EmployeeEntity with data from EmployeeDto
     * Note: This method does not update id, createdAt, and updatedAt fields
     * @param dto the DTO containing the new data
     * @param entity the entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(EmployeeDto dto, @MappingTarget EmployeeEntity entity);

    /**
     * Update an existing EmployeeEntity with data from EmployeeUpdateDto
     * Note: This method does not update id, createdAt, and updatedAt fields
     * Only updates fullname, position, and salary
     * @param updateDto the UpdateDTO containing the new data
     * @param entity the entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromUpdateDto(EmployeeUpdateDto updateDto, @MappingTarget EmployeeEntity entity);
}
