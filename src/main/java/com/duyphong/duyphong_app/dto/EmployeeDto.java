package com.duyphong.duyphong_app.dto;

import java.time.Instant;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeDto {
    private String id; 

    private String username;

    private String email;

    private String fullname;

    private String department;

    private String position;

    private Integer salary;

    private Instant createdAt;

    private Instant updatedAt;
}
