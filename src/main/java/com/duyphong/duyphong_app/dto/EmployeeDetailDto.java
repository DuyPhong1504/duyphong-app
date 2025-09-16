package com.duyphong.duyphong_app.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for detailed employee information including department and ongoing tasks
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeDetailDto {
    private String id;
    private String fullname;
    private String email;
    private String position;
    private Integer salary;
    private DepartmentInfo department;
    private List<TaskInfo> ongoingTasks;

    /**
     * Nested DTO for department information
     */
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class DepartmentInfo {
        private String id;
        private String name;
    }

    /**
     * Nested DTO for task information
     */
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class TaskInfo {
        private Integer id;
        private String taskName;
        private String description;
        private LocalDate dueDate;
        private String status;
    }
}
