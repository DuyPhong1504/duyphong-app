package com.duyphong.duyphong_app.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class DepartmentAverageSalaryResponse {
    private String departmentName;
    private Double averageSalary;
}