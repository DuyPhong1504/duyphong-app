package com.duyphong.duyphong_app.dto.response;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class DepartmentStatisticsResponse {
    
    private String departmentId;
    private String departmentName;
    private int totalEmployees;
    private Double averageSalary;
    private Map<String, Integer> taskCountsByStatus;
    private List<EmployeeResponse> newEmployees;
}