package com.duyphong.duyphong_app.repository;

import com.duyphong.duyphong_app.entity.LunchLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for LunchLog entity
 * Provides bulk save operations for Lunch Log data
 */
@Repository
public interface LunchLogRepository extends JpaRepository<LunchLogEntity, Integer> {
    // JpaRepository already provides saveAll() method for bulk operations
}