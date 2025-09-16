package com.duyphong.duyphong_app.repository;

import com.duyphong.duyphong_app.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
