package com.duyphong.duyphong_app.mapper;

import com.duyphong.duyphong_app.dto.response.DepartmentResponse;
import com.duyphong.duyphong_app.entity.DepartmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    /**
     * Convert DepartmentEntity to DepartmentResponse
     * @param entity the department entity to convert
     * @return the converted department response DTO
     */
    DepartmentResponse toDto(DepartmentEntity entity);

    /**
     * Convert DepartmentResponse to DepartmentEntity
     * @param dto the department response DTO to convert
     * @return the converted department entity
     */
    DepartmentEntity toEntity(DepartmentResponse dto);

    /**
     * Convert a list of DepartmentEntity to a list of DepartmentResponse
     * @param entities the list of department entities to convert
     * @return the converted list of department response DTOs
     */
    List<DepartmentResponse> toDtoList(List<DepartmentEntity> entities);

    /**
     * Convert a list of DepartmentResponse to a list of DepartmentEntity
     * @param dtos the list of department response DTOs to convert
     * @return the converted list of department entities
     */
    List<DepartmentEntity> toEntityList(List<DepartmentResponse> dtos);
}
