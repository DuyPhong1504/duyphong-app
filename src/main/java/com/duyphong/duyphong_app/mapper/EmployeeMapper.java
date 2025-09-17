package com.duyphong.duyphong_app.mapper;

import com.duyphong.duyphong_app.dto.request.UpdateEmployeeRequest;
import com.duyphong.duyphong_app.dto.response.EmployeeResponse;
import com.duyphong.duyphong_app.entity.DepartmentEntity;
import com.duyphong.duyphong_app.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /**
     * Convert EmployeeEntity to EmployeeResponse
     * @param entity the employee entity to convert
     * @return the converted employee response DTO
     */
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentToString")
    EmployeeResponse toDto(EmployeeEntity entity);

    /**
     * Convert EmployeeResponse to EmployeeEntity
     * @param dto the employee response DTO to convert
     * @return the converted employee entity
     */
    @Mapping(target = "department", source = "department", qualifiedByName = "stringToDepartment")
    EmployeeEntity toEntity(EmployeeResponse dto);

    /**
     * Convert a list of EmployeeEntity to a list of EmployeeResponse
     * @param entities the list of employee entities to convert
     * @return the list of converted employee response DTOs
     */
    List<EmployeeResponse> toDtoList(List<EmployeeEntity> entities);

    /**
     * Convert a list of EmployeeResponse to a list of EmployeeEntity
     * @param dtos the list of employee response DTOs to convert
     * @return the list of converted employee entities
     */
    List<EmployeeEntity> toEntityList(List<EmployeeResponse> dtos);

    /**
     * Update an existing EmployeeEntity with data from EmployeeResponse
     * Note: This method does not update id, createdAt, and updatedAt fields
     * @param dto the DTO containing the new data
     * @param entity the entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "department", source = "department", qualifiedByName = "stringToDepartment")
    void updateEntityFromDto(EmployeeResponse dto, @MappingTarget EmployeeEntity entity);

    /**
     * Update an existing EmployeeEntity with data from UpdateEmployeeRequest
     * Note: This method does not update id, createdAt, and updatedAt fields
     * Only updates fullname, position, and salary
     * @param updateRequest the UpdateRequest containing the new data
     * @param entity the entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromUpdateRequest(UpdateEmployeeRequest updateRequest, @MappingTarget EmployeeEntity entity);

    /**
     * Convert DepartmentEntity to String (department name)
     * @param department the department entity
     * @return the department name or null if department is null
     */
    @Named("departmentToString")
    default String mapDepartmentToString(DepartmentEntity department) {
        return department != null ? department.getName() : null;
    }

    /**
     * Convert String to DepartmentEntity (for reverse mapping)
     * Note: This creates a department entity with only the name field set
     * @param departmentName the department name
     * @return a department entity with the name set or null if name is null
     */
    @Named("stringToDepartment")
    default DepartmentEntity mapStringToDepartment(String departmentName) {
        if (departmentName == null) {
            return null;
        }
        DepartmentEntity department = new DepartmentEntity();
        department.setName(departmentName);
        return department;
    }
}
