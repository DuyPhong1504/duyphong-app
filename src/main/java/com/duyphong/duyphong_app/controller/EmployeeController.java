package com.duyphong.duyphong_app.controller;

import com.duyphong.duyphong_app.dto.request.UpdateEmployeeRequest;
import com.duyphong.duyphong_app.dto.request.UpdateEmployeeDepartmentRequest;
import com.duyphong.duyphong_app.dto.response.EmployeeDetailResponse;
import com.duyphong.duyphong_app.dto.response.EmployeeResponse;
import com.duyphong.duyphong_app.dto.response.UpdateEmployeeDepartmentResponse;
import com.duyphong.duyphong_app.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for Employee operations
 * Handles HTTP requests related to employee management
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Get employee by ID
     * @param id the employee ID as path variable
     * @return ResponseEntity containing EmployeeResponse if found, 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable @NotEmpty(message = "Employee ID cannot be empty") String id) {
        log.info("Received request to get employee with ID: {}", id);
        
        Optional<EmployeeResponse> employee = employeeService.findEmployeeById(id.trim());
        
        if (employee.isPresent()) {
            log.info("Successfully retrieved employee with ID: {}", id);
            return ResponseEntity.ok(employee.get());
        } else {
            log.warn("Employee not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get detailed employee information including department and ongoing tasks
     * @param id the employee ID as path variable
     * @return ResponseEntity containing EmployeeDetailResponse if found, 404 if not found
     */
    @GetMapping("/detail/{id}")
    public ResponseEntity<EmployeeDetailResponse> getEmployeeDetailById(@PathVariable @NotEmpty(message = "Employee ID cannot be empty") String id) {
        log.info("Received request to get employee detail with ID: {}", id);
        
        Optional<EmployeeDetailResponse> employeeDetail = employeeService.findEmployeeDetailById(id.trim());
        
        if (employeeDetail.isPresent()) {
            log.info("Successfully retrieved employee detail with ID: {}", id);
            return ResponseEntity.ok(employeeDetail.get());
        } else {
            log.warn("Employee not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update employee information (fullname, position, salary)
     * @param id the employee ID as path variable
     * @param updateRequest the request body containing employee update data (validated automatically)
     * @return ResponseEntity containing updated EmployeeResponse if successful, 404 if not found, 400 if validation fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable @NotEmpty(message = "Employee ID cannot be empty") String id, @Valid @RequestBody UpdateEmployeeRequest updateRequest) {
        log.info("Received request to update employee with ID: {}", id);
        
        log.debug("Update request data - Name: {}, Position: {}, Salary: {}", 
                 updateRequest.getFullname(), updateRequest.getPosition(), updateRequest.getSalary());
        
        Optional<EmployeeResponse> updatedEmployee = employeeService.updateEmployee(id.trim(), updateRequest);
        
        if (updatedEmployee.isPresent()) {
            log.info("Successfully updated employee with ID: {}", id);
            return ResponseEntity.ok(updatedEmployee.get());
        } else {
            log.warn("Employee not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update employee's department
     * @param employeeId the employee ID as path variable
     * @param request the request body containing new department ID (validated automatically)
     * @return ResponseEntity containing UpdateEmployeeDepartmentResponse if successful, 404 if not found, 400 if validation fails or same department
     */
    @PutMapping("/department/{employeeId}")
    public ResponseEntity<?> updateEmployeeDepartment(
            @PathVariable @NotEmpty(message = "Employee ID cannot be empty") String employeeId, 
            @Valid @RequestBody UpdateEmployeeDepartmentRequest request) {
        
        log.info("Received request to update department for employee ID: {} to department ID: {}", employeeId, request.getNewDepartmentId());
        
        try {
            Optional<UpdateEmployeeDepartmentResponse> result = employeeService.updateEmployeeDepartment(employeeId.trim(), request);
            
            if (result.isPresent()) {
                log.info("Successfully updated department for employee with ID: {}", employeeId);
                return ResponseEntity.ok(result.get());
            } else {
                log.warn("Employee or department not found - Employee ID: {}, Department ID: {}", employeeId, request.getNewDepartmentId());
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            log.warn("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "timestamp", java.time.LocalDateTime.now(),
                "status", 400,
                "error", "Bad Request",
                "message", e.getMessage()
            ));
        }
    }
}