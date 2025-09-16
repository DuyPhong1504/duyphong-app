package com.duyphong.duyphong_app.service;

import com.duyphong.duyphong_app.dto.EmployeeDto;
import com.duyphong.duyphong_app.dto.EmployeeUpdateDto;
import com.duyphong.duyphong_app.entity.EmployeeEntity;
import com.duyphong.duyphong_app.mapper.EmployeeMapper;
import com.duyphong.duyphong_app.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service layer for Employee operations
 * Contains business logic for employee management
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * Find employee by ID and return as DTO
     * @param id the employee ID
     * @return Optional containing EmployeeDto if found, empty otherwise
     */
    public Optional<EmployeeDto> findEmployeeById(String id) {
        log.info("Finding employee with ID: {}", id);
        
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        
        if (employeeEntity.isPresent()) {
            log.info("Employee found with ID: {}", id);
            EmployeeDto employeeDto = employeeMapper.toDto(employeeEntity.get());
            return Optional.of(employeeDto);
        } else {
            log.warn("Employee not found with ID: {}", id);
            return Optional.empty();
        }
    }

    /**
     * Update employee information (fullname, position, salary)
     * Only updates fields that are not null in the updateDto
     * @param id the employee ID
     * @param updateDto the DTO containing updated information
     * @return Optional containing updated EmployeeDto if successful, empty if employee not found
     */
    @Transactional
    public Optional<EmployeeDto> updateEmployee(String id, EmployeeUpdateDto updateDto) {
        log.info("Updating employee with ID: {}", id);
        
        Optional<EmployeeEntity> employeeEntityOpt = employeeRepository.findById(id);
        
        if (employeeEntityOpt.isPresent()) {
            EmployeeEntity employeeEntity = employeeEntityOpt.get();
            
            // Log current values
            log.debug("Current employee data - Name: {}, Position: {}, Salary: {}", 
                     employeeEntity.getFullname(), employeeEntity.getPosition(), employeeEntity.getSalary());
            
            // Update only non-null fields
            if (updateDto.getFullname() != null) {
                employeeEntity.setFullname(updateDto.getFullname());
            }
            if (updateDto.getPosition() != null) {
                employeeEntity.setPosition(updateDto.getPosition());
            }
            if (updateDto.getSalary() != null) {
                employeeEntity.setSalary(updateDto.getSalary());
            }
            
            // Save the updated entity
            EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
            
            // Log updated values
            log.info("Employee updated successfully - ID: {}, Name: {}, Position: {}, Salary: {}", 
                    savedEntity.getId(), savedEntity.getFullname(), savedEntity.getPosition(), savedEntity.getSalary());
            
            // Convert to DTO and return
            EmployeeDto employeeDto = employeeMapper.toDto(savedEntity);
            return Optional.of(employeeDto);
        } else {
            log.warn("Employee not found with ID: {}", id);
            return Optional.empty();
        }
    }

}