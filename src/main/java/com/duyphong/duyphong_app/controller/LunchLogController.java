package com.duyphong.duyphong_app.controller;

import com.duyphong.duyphong_app.dto.request.BulkCreateLunchLogRequest;
import com.duyphong.duyphong_app.dto.response.BulkCreateLunchLogResponse;
import com.duyphong.duyphong_app.service.LunchLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Lunch Log operations
 * Handles HTTP requests for bulk lunch log creation
 */
@RestController
@RequestMapping("/api/lunch-logs")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Lunch Log Management", description = "APIs for bulk lunch log creation")
public class LunchLogController {

    private final LunchLogService lunchLogService;

    /**
     * Create multiple lunch logs at once
     * POST /api/lunch-logs/bulk
     */
    @PostMapping("/bulk")
    @Operation(summary = "Create multiple lunch logs", 
               description = "Creates multiple lunch log records in a single request")
    public ResponseEntity<BulkCreateLunchLogResponse> createBulkLunchLogs(
            @Valid @RequestBody BulkCreateLunchLogRequest request) {
        
        log.info("Received request to create {} lunch logs", request.getLunchLogs().size());
        
        BulkCreateLunchLogResponse response = lunchLogService.createBulkLunchLogs(request);
        
        log.info("Successfully processed bulk lunch log creation");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}