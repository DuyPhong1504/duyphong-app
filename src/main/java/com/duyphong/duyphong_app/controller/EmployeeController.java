package com.duyphong.duyphong_app.controller;

import com.duyphong.duyphong_app.dto.EmployeeDto;
import com.duyphong.duyphong_app.dto.EmployeeUpdateDto;
import com.duyphong.duyphong_app.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST Controller for Employee operations
 * Handles HTTP requests related to employee management
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Get employee by ID
     * @param id the employee ID as path variable
     * @return ResponseEntity containing EmployeeDto if found, 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable String id) {
        log.info("Received request to get employee with ID: {}", id);
        
        // Validate input
        if (id == null || id.trim().isEmpty()) {
            log.warn("Invalid employee ID provided: {}", id);
            return ResponseEntity.badRequest().build();
        }
        
        Optional<EmployeeDto> employee = employeeService.findEmployeeById(id.trim());
        
        if (employee.isPresent()) {
            log.info("Successfully retrieved employee with ID: {}", id);
            return ResponseEntity.ok(employee.get());
        } else {
            log.warn("Employee not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update employee information (fullname, position, salary)
     * @param id the employee ID as path variable
     * @param updateDto the request body containing employee update data (validated automatically)
     * @return ResponseEntity containing updated EmployeeDto if successful, 404 if not found, 400 if validation fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable String id, @Valid @RequestBody EmployeeUpdateDto updateDto) {
        log.info("Received request to update employee with ID: {}", id);
        
        // Validate path variable
        if (id == null || id.trim().isEmpty()) {
            log.warn("Invalid employee ID provided: {}", id);
            return ResponseEntity.badRequest().build();
        }
        
        log.debug("Update request data - Name: {}, Position: {}, Salary: {}", 
                 updateDto.getFullname(), updateDto.getPosition(), updateDto.getSalary());
        
        Optional<EmployeeDto> updatedEmployee = employeeService.updateEmployee(id.trim(), updateDto);
        
        if (updatedEmployee.isPresent()) {
            log.info("Successfully updated employee with ID: {}", id);
            return ResponseEntity.ok(updatedEmployee.get());
        } else {
            log.warn("Employee not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}