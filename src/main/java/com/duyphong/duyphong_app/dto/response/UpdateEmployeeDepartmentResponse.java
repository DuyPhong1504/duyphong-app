package com.duyphong.duyphong_app.dto.response;

import lombok.*;

import java.time.Instant;

/**
 * DTO for employee department update response
 * Contains employee information and department change details
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateEmployeeDepartmentResponse {
    
    private String employeeId;
    private String employeeFullname;
    private String employeeEmail;
    private String employeePosition;
    private Integer employeeSalary;
    
    private String oldDepartmentId;
    private String oldDepartmentName;
    
    private String newDepartmentId;
    private String newDepartmentName;
    
    private Instant changeDate;
    private String message;
}
