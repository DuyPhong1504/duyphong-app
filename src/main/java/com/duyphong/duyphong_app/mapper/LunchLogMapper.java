package com.duyphong.duyphong_app.mapper;

import com.duyphong.duyphong_app.dto.request.CreateLunchLogRequest;
import com.duyphong.duyphong_app.dto.response.LunchLogResponse;
import com.duyphong.duyphong_app.entity.LunchLogEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LunchLogMapper {

    /**
     * Convert LunchLogEntity to LunchLogResponse
     * @param entity the lunch log entity to convert
     * @return the converted lunch log response DTO
     */
    public LunchLogResponse toResponse(LunchLogEntity entity) {
        if (entity == null) {
            return null;
        }

        return LunchLogResponse.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployeeId())
                .lunchDate(entity.getLunchDate())
                .mealType(entity.getMealType())
                .restaurant(entity.getRestaurant())
                .notes(entity.getNotes())
                .build();
    }

    /**
     * Convert CreateLunchLogRequest to LunchLogEntity (for creation)
     * @param request the lunch log creation request to convert
     * @return the converted lunch log entity
     */
    public LunchLogEntity toEntity(CreateLunchLogRequest request) {
        if (request == null) {
            return null;
        }

        return LunchLogEntity.builder()
                .employeeId(request.getEmployeeId())
                .lunchDate(request.getLunchDate())
                .mealType(request.getMealType())
                .restaurant(request.getRestaurant())
                .notes(request.getNotes())
                .build();
    }

    /**
     * Convert list of CreateLunchLogRequest to list of LunchLogEntity
     * @param requests the list of lunch log creation requests to convert
     * @return the converted list of lunch log entities
     */
    public List<LunchLogEntity> toEntityList(List<CreateLunchLogRequest> requests) {
        if (requests == null) {
            return null;
        }

        return requests.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Convert list of LunchLogEntity to list of LunchLogResponse
     * @param entities the list of lunch log entities to convert
     * @return the converted list of lunch log response DTOs
     */
    public List<LunchLogResponse> toResponseList(List<LunchLogEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}