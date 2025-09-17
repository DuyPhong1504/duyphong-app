package com.duyphong.duyphong_app.service;

import com.duyphong.duyphong_app.dto.response.DepartmentAverageSalaryResponse;
import com.duyphong.duyphong_app.dto.response.DepartmentResponse;
import com.duyphong.duyphong_app.entity.DepartmentEntity;
import com.duyphong.duyphong_app.mapper.DepartmentMapper;
import com.duyphong.duyphong_app.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final DepartmentMapper departmentMapper;

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
}
