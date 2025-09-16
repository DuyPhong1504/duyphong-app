package com.duyphong.duyphong_app.mapper;

import com.duyphong.duyphong_app.dto.DepartmentDto;
import com.duyphong.duyphong_app.entity.DepartmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    /**
     * Convert DepartmentEntity to DepartmentDto
     * @param entity the department entity to convert
     * @return the converted department DTO
     */
    DepartmentDto toDto(DepartmentEntity entity);

    /**
     * Convert DepartmentDto to DepartmentEntity
     * @param dto the department DTO to convert
     * @return the converted department entity
     */
    DepartmentEntity toEntity(DepartmentDto dto);

    /**
     * Convert a list of DepartmentEntity to a list of DepartmentDto
     * @param entities the list of department entities to convert
     * @return the converted list of department DTOs
     */
    List<DepartmentDto> toDtoList(List<DepartmentEntity> entities);

    /**
     * Convert a list of DepartmentDto to a list of DepartmentEntity
     * @param dtos the list of department DTOs to convert
     * @return the converted list of department entities
     */
    List<DepartmentEntity> toEntityList(List<DepartmentDto> dtos);
}
