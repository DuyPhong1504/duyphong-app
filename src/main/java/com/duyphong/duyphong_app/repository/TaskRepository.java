package com.duyphong.duyphong_app.repository;

import com.duyphong.duyphong_app.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Task entity
 * Provides CRUD operations and custom query methods for Task data
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    
}
