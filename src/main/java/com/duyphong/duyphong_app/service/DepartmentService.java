package com.duyphong.duyphong_app.service;

import com.duyphong.duyphong_app.dto.DepartmentDto;
import com.duyphong.duyphong_app.entity.DepartmentEntity;
import com.duyphong.duyphong_app.mapper.DepartmentMapper;
import com.duyphong.duyphong_app.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
     * @return List of DepartmentDto
     */
    public List<DepartmentDto> getAllDepartments() {
        log.info("Retrieving all departments");
        
        List<DepartmentEntity> departments = departmentRepository.findAll();
        log.info("Found {} departments", departments.size());
        
        return departmentMapper.toDtoList(departments);
    }

    /**
     * Create a new department
     * @param name the department name
     * @return the created DepartmentDto
     * @throws IllegalArgumentException if department name already exists
     */
    @Transactional
    public DepartmentDto createDepartment(String name) {
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
}
