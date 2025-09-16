package com.duyphong.duyphong_app.repository;

import com.duyphong.duyphong_app.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}