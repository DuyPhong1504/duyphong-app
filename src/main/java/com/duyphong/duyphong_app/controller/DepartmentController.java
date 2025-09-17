package com.duyphong.duyphong_app.controller;

import com.duyphong.duyphong_app.dto.request.CreateDepartmentRequest;
import com.duyphong.duyphong_app.dto.response.DepartmentResponse;
import com.duyphong.duyphong_app.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Department operations
 * Handles HTTP requests related to department management
 */
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * Get all departments
     * @return ResponseEntity containing list of DepartmentResponse
     */
    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        log.info("Received request to get all departments");
        
        List<DepartmentResponse> departments = departmentService.getAllDepartments();
        
        log.info("Successfully retrieved {} departments", departments.size());
        return ResponseEntity.ok(departments);
    }

    /**
     * Create a new department
     * @param request the department creation request containing the name
     * @return ResponseEntity containing the created DepartmentResponse
     */
    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        log.info("Received request to create department with name: {}", request.getName());
        
        try {
            DepartmentResponse createdDepartment = departmentService.createDepartment(request.getName());
            log.info("Successfully created department with ID: {}", createdDepartment.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        } catch (IllegalArgumentException e) {
            log.error("Failed to create department: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
