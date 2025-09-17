package com.duyphong.duyphong_app.service;

import com.duyphong.duyphong_app.dto.request.BulkCreateLunchLogRequest;
import com.duyphong.duyphong_app.dto.request.CreateLunchLogRequest;
import com.duyphong.duyphong_app.dto.response.BulkCreateLunchLogResponse;
import com.duyphong.duyphong_app.dto.response.LunchLogResponse;
import com.duyphong.duyphong_app.entity.LunchLogEntity;
import com.duyphong.duyphong_app.enumeration.MealType;
import com.duyphong.duyphong_app.mapper.LunchLogMapper;
import com.duyphong.duyphong_app.repository.LunchLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for Lunch Log operations
 * Contains business logic for lunch log management
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LunchLogService {

    private final LunchLogRepository lunchLogRepository;
    private final LunchLogMapper lunchLogMapper;


    /**
     * Create multiple lunch logs at once (bulk operation)
     * @param request the bulk lunch log creation request
     * @return the bulk creation response with all created lunch logs
     */
    @Transactional
    public BulkCreateLunchLogResponse createBulkLunchLogs(BulkCreateLunchLogRequest request) {
        log.info("Starting bulk creation of {} lunch logs", request.getLunchLogs().size());
        
        // Convert requests to entities
        List<LunchLogEntity> lunchLogEntities = lunchLogMapper.toEntityList(request.getLunchLogs());
        
        // Save all entities at once
        List<LunchLogEntity> savedLunchLogs = lunchLogRepository.saveAll(lunchLogEntities);
        
        log.info("Successfully created {} lunch logs", savedLunchLogs.size());
        
        // Convert saved entities back to response DTOs
        List<LunchLogResponse> lunchLogResponses = lunchLogMapper.toResponseList(savedLunchLogs);
        
        // Build and return bulk response
        return BulkCreateLunchLogResponse.builder()
                .totalCreated(savedLunchLogs.size())
                .lunchLogs(lunchLogResponses)
                .message("Successfully created " + savedLunchLogs.size() + " lunch log entries")
                .build();
    }

}