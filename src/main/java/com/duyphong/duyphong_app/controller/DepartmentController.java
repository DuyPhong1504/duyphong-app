package com.duyphong.duyphong_app.controller;

import com.duyphong.duyphong_app.dto.DepartmentDto;
import com.duyphong.duyphong_app.service.DepartmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
     * @return ResponseEntity containing list of DepartmentDto
     */
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        log.info("Received request to get all departments");
        
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        
        log.info("Successfully retrieved {} departments", departments.size());
        return ResponseEntity.ok(departments);
    }

    /**
     * Create a new department
     * @param request the department creation request containing the name
     * @return ResponseEntity containing the created DepartmentDto
     */
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        log.info("Received request to create department with name: {}", request.getName());
        
        try {
            DepartmentDto createdDepartment = departmentService.createDepartment(request.getName());
            log.info("Successfully created department with ID: {}", createdDepartment.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        } catch (IllegalArgumentException e) {
            log.error("Failed to create department: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Request DTO for creating a department
     */
    public static class CreateDepartmentRequest {
        @NotBlank(message = "Department name is required")
        private String name;

        public CreateDepartmentRequest() {}

        public CreateDepartmentRequest(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
