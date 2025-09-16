package com.duyphong.duyphong_app.repository;

import com.duyphong.duyphong_app.entity.DepartmentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for DepartmentHistory entity
 * Provides CRUD operations and custom query methods for DepartmentHistory data
 */
@Repository
public interface DepartmentHistoryRepository extends JpaRepository<DepartmentHistoryEntity, Integer> {
    
    /**
     * Find all department history records for a specific employee
     * @param employeeId the employee ID
     * @return List of department history records ordered by change date descending
     */
    @Query("SELECT dh FROM DepartmentHistoryEntity dh WHERE dh.employeeId = :employeeId ORDER BY dh.changeDate DESC")
    List<DepartmentHistoryEntity> findByEmployeeIdOrderByChangeDateDesc(@Param("employeeId") String employeeId);
    
    /**
     * Find the latest department change for a specific employee
     * @param employeeId the employee ID
     * @return Optional containing the latest department history record if exists
     */
    @Query("SELECT dh FROM DepartmentHistoryEntity dh WHERE dh.employeeId = :employeeId ORDER BY dh.changeDate DESC LIMIT 1")
    DepartmentHistoryEntity findLatestByEmployeeId(@Param("employeeId") String employeeId);
}
