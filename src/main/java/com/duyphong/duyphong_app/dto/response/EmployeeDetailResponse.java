package com.duyphong.duyphong_app.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeDetailResponse {
    private String id;
    private String fullname;
    private String email;
    private String position;
    private Integer salary;
    private DepartmentInfo department;
    private List<TaskInfo> ongoingTasks;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class DepartmentInfo {
        private String id;
        private String name;
    }

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