package com.duyphong.duyphong_app.repository;

import com.duyphong.duyphong_app.entity.TaskEntity;
import com.duyphong.duyphong_app.enumeration.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Task entity
 * Provides CRUD operations and custom query methods for Task data
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    
    /**
     * Find tasks by employee ID and status
     * @param employeeId the employee ID
     * @param status the task status
     * @return List of tasks matching the criteria
     */
    List<TaskEntity> findByEmployeeIdAndStatus(String employeeId, TaskStatus status);

    /**
     * Find tasks with optional filtering criteria and fetch employee details
     * Supports filtering by employee_id, status, and due_date
     * @param employeeId the employee ID (optional)
     * @param status the task status (optional)
     * @param dueDate the due date (optional)
     * @return List of tasks matching the criteria with employee information
     */
    @Query("SELECT t FROM TaskEntity t " +
           "LEFT JOIN FETCH t.employee e " +
           "LEFT JOIN FETCH e.department d " +
           "WHERE 1 = 1 " +
           "AND (:employeeId IS NULL OR t.employeeId = :employeeId) " +
           "AND (:status IS NULL OR t.status = :status) " +
           "AND (:dueDate IS NULL OR t.dueDate = :dueDate)")
    List<TaskEntity> findTasksWithFilters(@Param("employeeId") String employeeId,
                                         @Param("status") TaskStatus status,
                                         @Param("dueDate") LocalDate dueDate);
    
    /**
     * Count tasks by department ID and status
     * @param departmentId the department ID
     * @param status the task status
     * @return the number of tasks matching the criteria
     */
    @Query("SELECT COUNT(t) FROM TaskEntity t JOIN t.employee e WHERE e.department.id = :departmentId AND t.status = :status")
    Long countTasksByDepartmentIdAndStatus(@Param("departmentId") String departmentId, @Param("status") TaskStatus status);
}
