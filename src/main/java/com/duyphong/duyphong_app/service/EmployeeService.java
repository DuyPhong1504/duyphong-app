package com.duyphong.duyphong_app.service;

import com.duyphong.duyphong_app.dto.request.UpdateEmployeeDepartmentRequest;
import com.duyphong.duyphong_app.dto.request.UpdateEmployeeRequest;
import com.duyphong.duyphong_app.dto.response.EmployeeDetailResponse;
import com.duyphong.duyphong_app.dto.response.EmployeeResponse;
import com.duyphong.duyphong_app.dto.response.UpdateEmployeeDepartmentResponse;
import com.duyphong.duyphong_app.entity.DepartmentEntity;
import com.duyphong.duyphong_app.entity.DepartmentHistoryEntity;
import com.duyphong.duyphong_app.entity.EmployeeEntity;
import com.duyphong.duyphong_app.entity.TaskEntity;
import com.duyphong.duyphong_app.enumeration.TaskStatus;
import com.duyphong.duyphong_app.mapper.EmployeeMapper;
import com.duyphong.duyphong_app.repository.DepartmentHistoryRepository;
import com.duyphong.duyphong_app.repository.DepartmentRepository;
import com.duyphong.duyphong_app.repository.EmployeeRepository;
import com.duyphong.duyphong_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final TaskRepository taskRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentHistoryRepository departmentHistoryRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * Find employee by ID and return as DTO
     * @param id the employee ID
     * @return Optional containing EmployeeResponse if found, empty otherwise
     */
    public Optional<EmployeeResponse> findEmployeeById(String id) {
        log.info("Finding employee with ID: {}", id);
        
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        
        if (employeeEntity.isPresent()) {
            log.info("Employee found with ID: {}", id);
            EmployeeResponse employeeResponse = employeeMapper.toDto(employeeEntity.get());
            return Optional.of(employeeResponse);
        } else {
            log.warn("Employee not found with ID: {}", id);
            return Optional.empty();
        }
    }

    /**
     * Find employee detail by ID including department and ongoing tasks
     * @param id the employee ID
     * @return Optional containing EmployeeDetailResponse if found, empty otherwise
     */
    public Optional<EmployeeDetailResponse> findEmployeeDetailById(String id) {
        log.info("Finding employee detail with ID: {}", id);
        
        Optional<EmployeeEntity> employeeEntityOpt = employeeRepository.findByIdWithDepartment(id);
        
        if (employeeEntityOpt.isPresent()) {
            EmployeeEntity employee = employeeEntityOpt.get();
            log.info("Employee found with ID: {}", id);
            
            // Get ongoing tasks (IN_PROGRESS status)
            List<TaskEntity> ongoingTasks = taskRepository.findByEmployeeIdAndStatus(id, TaskStatus.IN_PROGRESS);
            log.debug("Found {} ongoing tasks for employee: {}", ongoingTasks.size(), id);
            
            // Build department info
            EmployeeDetailResponse.DepartmentInfo departmentInfo = null;
            if (employee.getDepartment() != null) {
                departmentInfo = EmployeeDetailResponse.DepartmentInfo.builder()
                        .id(employee.getDepartment().getId())
                        .name(employee.getDepartment().getName())
                        .build();
            }
            
            // Build task info list
            List<EmployeeDetailResponse.TaskInfo> taskInfoList = ongoingTasks.stream()
                    .map(task -> EmployeeDetailResponse.TaskInfo.builder()
                            .id(task.getId())
                            .taskName(task.getTaskName())
                            .description(task.getDescription())
                            .dueDate(task.getDueDate())
                            .status(task.getStatus().name())
                            .build())
                    .collect(Collectors.toList());
            
            // Build employee detail DTO
            EmployeeDetailResponse employeeDetailResponse = EmployeeDetailResponse.builder()
                    .id(employee.getId())
                    .fullname(employee.getFullname())
                    .email(employee.getEmail())
                    .position(employee.getPosition())
                    .salary(employee.getSalary())
                    .department(departmentInfo)
                    .ongoingTasks(taskInfoList)
                    .build();
            
            return Optional.of(employeeDetailResponse);
        } else {
            log.warn("Employee not found with ID: {}", id);
            return Optional.empty();
        }
    }

    /**
     * Update employee information (fullname, position, salary)
     * Only updates fields that are not null in the updateRequest
     * @param id the employee ID
     * @param updateRequest the request containing updated information
     * @return Optional containing updated EmployeeResponse if successful, empty if employee not found
     */
    @Transactional
    public Optional<EmployeeResponse> updateEmployee(String id, UpdateEmployeeRequest updateRequest) {
        log.info("Updating employee with ID: {}", id);
        
        Optional<EmployeeEntity> employeeEntityOpt = employeeRepository.findById(id);
        
        if (employeeEntityOpt.isPresent()) {
            EmployeeEntity employeeEntity = employeeEntityOpt.get();
            
            // Log current values
            log.debug("Current employee data - Name: {}, Position: {}, Salary: {}", 
                     employeeEntity.getFullname(), employeeEntity.getPosition(), employeeEntity.getSalary());
            
            // Update only non-null fields
            if (updateRequest.getFullname() != null) {
                employeeEntity.setFullname(updateRequest.getFullname());
            }
            if (updateRequest.getPosition() != null) {
                employeeEntity.setPosition(updateRequest.getPosition());
            }
            if (updateRequest.getSalary() != null) {
                employeeEntity.setSalary(updateRequest.getSalary());
            }
            
            // Save the updated entity
            EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
            
            // Log updated values
            log.info("Employee updated successfully - ID: {}, Name: {}, Position: {}, Salary: {}", 
                    savedEntity.getId(), savedEntity.getFullname(), savedEntity.getPosition(), savedEntity.getSalary());
            
            // Convert to DTO and return
            EmployeeResponse employeeResponse = employeeMapper.toDto(savedEntity);
            return Optional.of(employeeResponse);
        } else {
            log.warn("Employee not found with ID: {}", id);
            return Optional.empty();
        }
    }

    /**
     * Update employee's department and create history record
     * This operation is transactional - either both operations succeed or both fail
     * @param employeeId the employee ID
     * @param request the request containing new department ID
     * @return Optional containing UpdateEmployeeDepartmentResponse if successful, empty if employee or department not found
     * @throws IllegalArgumentException if the new department is the same as current department
     */
    @Transactional
    public Optional<UpdateEmployeeDepartmentResponse> updateEmployeeDepartment(String employeeId, UpdateEmployeeDepartmentRequest request) {
        log.info("Updating department for employee ID: {} to department ID: {}", employeeId, request.getNewDepartmentId());
        
        // Find employee with current department
        Optional<EmployeeEntity> employeeEntityOpt = employeeRepository.findByIdWithDepartment(employeeId);
        if (employeeEntityOpt.isEmpty()) {
            log.warn("Employee not found with ID: {}", employeeId);
            return Optional.empty();
        }
        
        EmployeeEntity employee = employeeEntityOpt.get();
        
        // Find the new department
        Optional<DepartmentEntity> newDepartmentOpt = departmentRepository.findById(request.getNewDepartmentId());
        if (newDepartmentOpt.isEmpty()) {
            log.warn("Department not found with ID: {}", request.getNewDepartmentId());
            return Optional.empty();
        }
        
        DepartmentEntity newDepartment = newDepartmentOpt.get();
        DepartmentEntity oldDepartment = employee.getDepartment();
        
        // Check if it's actually a change
        if (oldDepartment != null && oldDepartment.getId().equals(request.getNewDepartmentId())) {
            log.warn("Employee {} is already in department {}", employeeId, request.getNewDepartmentId());
            throw new IllegalArgumentException("Employee is already in the specified department");
        }
        
        // Create department history record
        DepartmentHistoryEntity historyEntity = DepartmentHistoryEntity.builder()
                .employeeId(employeeId)
                .oldDepartmentId(oldDepartment != null ? oldDepartment.getId() : null)
                .newDepartmentId(request.getNewDepartmentId())
                .changeDate(java.time.Instant.now())
                .build();
        
        // Save history record first
        DepartmentHistoryEntity savedHistory = departmentHistoryRepository.save(historyEntity);
        log.info("Created department history record with ID: {}", savedHistory.getId());
        
        // Update employee's department
        employee.setDepartment(newDepartment);
        EmployeeEntity savedEmployee = employeeRepository.save(employee);
        log.info("Updated employee {} department from {} to {}", 
                employeeId, 
                oldDepartment != null ? oldDepartment.getId() : "null", 
                newDepartment.getId());
        
        // Create response
        UpdateEmployeeDepartmentResponse response = UpdateEmployeeDepartmentResponse.builder()
                .employeeId(savedEmployee.getId())
                .employeeFullname(savedEmployee.getFullname())
                .employeeEmail(savedEmployee.getEmail())
                .employeePosition(savedEmployee.getPosition())
                .employeeSalary(savedEmployee.getSalary())
                .oldDepartmentId(oldDepartment != null ? oldDepartment.getId() : null)
                .oldDepartmentName(oldDepartment != null ? oldDepartment.getName() : null)
                .newDepartmentId(newDepartment.getId())
                .newDepartmentName(newDepartment.getName())
                .changeDate(savedHistory.getChangeDate())
                .message("Employee department updated successfully")
                .build();
        
        return Optional.of(response);
    }

}