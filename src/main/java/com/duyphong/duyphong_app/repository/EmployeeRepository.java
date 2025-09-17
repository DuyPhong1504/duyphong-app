package com.duyphong.duyphong_app.repository;

import com.duyphong.duyphong_app.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Employee entity
 * Provides CRUD operations and custom query methods for Employee data
 */
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
    
    /**
     * Find employee by ID
     * @param id the employee ID
     * @return Optional containing the employee if found, empty otherwise
     */
    Optional<EmployeeEntity> findById(String id);
    
    /**
     * Find employee by ID with department eagerly loaded
     * @param id the employee ID
     * @return Optional containing the employee with department if found, empty otherwise
     */
    @Query("SELECT e FROM EmployeeEntity e LEFT JOIN FETCH e.department WHERE e.id = :id")
    Optional<EmployeeEntity> findByIdWithDepartment(@Param("id") String id);
    
    /**
     * Count employees by department ID
     * @param departmentId the department ID
     * @return the number of employees in the department
     */
    @Query("SELECT COUNT(e) FROM EmployeeEntity e WHERE e.department.id = :departmentId")
    Long countByDepartmentId(@Param("departmentId") String departmentId);
    
    /**
     * Get average salary by department ID
     * @param departmentId the department ID
     * @return the average salary of employees in the department
     */
    @Query("SELECT AVG(e.salary) FROM EmployeeEntity e WHERE e.department.id = :departmentId AND e.salary IS NOT NULL")
    Double getAverageSalaryByDepartmentId(@Param("departmentId") String departmentId);
    
    /**
     * Find employees by department ID who joined within the specified time period
     * @param departmentId the department ID
     * @param sinceDate the date since when to find new employees
     * @return list of employees who joined after the specified date
     */
    @Query("SELECT e FROM EmployeeEntity e WHERE e.department.id = :departmentId AND e.createdAt >= :sinceDate")
    List<EmployeeEntity> findNewEmployeesByDepartmentIdSince(@Param("departmentId") String departmentId, @Param("sinceDate") Instant sinceDate);
}