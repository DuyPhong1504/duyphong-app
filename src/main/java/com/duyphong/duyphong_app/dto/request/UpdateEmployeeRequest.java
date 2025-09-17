package com.duyphong.duyphong_app.dto.request;

import com.duyphong.duyphong_app.utils.AtLeastOneField;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@AtLeastOneField(message = "At least one field (fullname, position, or salary) must be provided for update")
public class UpdateEmployeeRequest {
    
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullname;
    
    @Size(min = 2, max = 50, message = "Position must be between 2 and 50 characters")
    private String position;
    
    @Min(value = 1, message = "Salary must be greater than 0")
    private Integer salary;
}