package com.duyphong.duyphong_app.repository;

import com.duyphong.duyphong_app.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Department entity
 * Provides CRUD operations and custom query methods for Department data
 */
@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, String> {
    
    /**
     * Check if department name already exists
     * @param name the department name
     * @return true if exists, false otherwise
     */
    boolean existsByName(String name);
    
    /**
     * Get department names with their average employee salary
     * @return List of Object arrays containing department name and average salary
     */
    @Query("SELECT d.name, AVG(e.salary) FROM DepartmentEntity d " +
           "LEFT JOIN EmployeeEntity e ON e.department.id = d.id " +
           "GROUP BY d.id, d.name")
    List<Object[]> findDepartmentAverageSalaries();
}
