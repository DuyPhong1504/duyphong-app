package com.duyphong.duyphong_app.service;

import com.duyphong.duyphong_app.dto.response.DepartmentAverageSalaryResponse;
import com.duyphong.duyphong_app.dto.response.DepartmentResponse;
import com.duyphong.duyphong_app.dto.response.DepartmentStatisticsResponse;
import com.duyphong.duyphong_app.dto.response.EmployeeResponse;
import com.duyphong.duyphong_app.entity.DepartmentEntity;
import com.duyphong.duyphong_app.entity.EmployeeEntity;
import com.duyphong.duyphong_app.enumeration.TaskStatus;
import com.duyphong.duyphong_app.mapper.DepartmentMapper;
import com.duyphong.duyphong_app.mapper.EmployeeMapper;
import com.duyphong.duyphong_app.repository.DepartmentRepository;
import com.duyphong.duyphong_app.repository.EmployeeRepository;
import com.duyphong.duyphong_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service layer for Department operations
 * Contains business logic for department management
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper employeeMapper;

    /**
     * Get all departments
     * @return List of DepartmentResponse
     */
    public List<DepartmentResponse> getAllDepartments() {
        log.info("Retrieving all departments");
        
        List<DepartmentEntity> departments = departmentRepository.findAll();
        log.info("Found {} departments", departments.size());
        
        return departmentMapper.toDtoList(departments);
    }

    /**
     * Create a new department
     * @param name the department name
     * @return the created DepartmentResponse
     * @throws IllegalArgumentException if department name already exists
     */
    @Transactional
    public DepartmentResponse createDepartment(String name) {
        log.info("Creating new department with name: {}", name);
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty");
        }
        
        String trimmedName = name.trim();
        
        if (departmentRepository.existsByName(trimmedName)) {
            log.warn("Department with name '{}' already exists", trimmedName);
            throw new IllegalArgumentException("Department with name '" + trimmedName + "' already exists");
        }
        
        DepartmentEntity departmentEntity = DepartmentEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(trimmedName)
                .build();
        
        DepartmentEntity savedEntity = departmentRepository.save(departmentEntity);
        log.info("Successfully created department with ID: {} and name: {}", savedEntity.getId(), savedEntity.getName());
        
        return departmentMapper.toDto(savedEntity);
    }
    
    /**
     * Get department average salaries
     * @return List of DepartmentAverageSalaryResponse containing department names and their average employee salaries
     */
    public List<DepartmentAverageSalaryResponse> getDepartmentAverageSalaries() {
        log.info("Retrieving department average salaries");
        
        List<Object[]> results = departmentRepository.findDepartmentAverageSalaries();
        log.info("Found {} departments with salary data", results.size());
        
        return results.stream()
                .map(result -> DepartmentAverageSalaryResponse.builder()
                        .departmentName((String) result[0])
                        .averageSalary(result[1] != null ? ((Number) result[1]).doubleValue() : 0.0)
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * Get comprehensive department statistics
     * @param departmentId the department ID
     * @return DepartmentStatisticsResponse containing all aggregated department information
     * @throws IllegalArgumentException if department does not exist
     */
    public DepartmentStatisticsResponse getDepartmentStatistics(String departmentId) {
        log.info("Retrieving statistics for department: {}", departmentId);
        
        // Verify department exists
        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));
        
        // Get total number of employees
        Long totalEmployees = employeeRepository.countByDepartmentId(departmentId);
        
        // Get average salary
        Double averageSalary = employeeRepository.getAverageSalaryByDepartmentId(departmentId);
        if (averageSalary == null) {
            averageSalary = 0.0;
        }
        
        // Get task counts by status
        Map<String, Integer> taskCountsByStatus = new HashMap<>();
        for (TaskStatus status : TaskStatus.values()) {
            Long count = taskRepository.countTasksByDepartmentIdAndStatus(departmentId, status);
            taskCountsByStatus.put(status.name(), count.intValue());
        }
        
        // Get new employees (last 30 days)
        Instant thirtyDaysAgo = Instant.now().minus(30, ChronoUnit.DAYS);
        List<EmployeeEntity> newEmployeeEntities = employeeRepository
                .findNewEmployeesByDepartmentIdSince(departmentId, thirtyDaysAgo);
        
        List<EmployeeResponse> newEmployees = employeeMapper.toDtoList(newEmployeeEntities);
        
        log.info("Department {} statistics: {} employees, avg salary: {}, task counts: {}, {} new employees", 
                departmentId, totalEmployees, averageSalary, taskCountsByStatus, newEmployees.size());
        
        return DepartmentStatisticsResponse.builder()
                .departmentId(department.getId())
                .departmentName(department.getName())
                .totalEmployees(totalEmployees.intValue())
                .averageSalary(averageSalary)
                .taskCountsByStatus(taskCountsByStatus)
                .newEmployees(newEmployees)
                .build();
    }
}
