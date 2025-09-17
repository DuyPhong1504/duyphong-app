package com.duyphong.duyphong_app.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * Request DTO for bulk lunch log registration
 * Accepts a list of lunch log entries to be saved at once
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class BulkCreateLunchLogRequest {

    @NotEmpty(message = "Lunch logs list cannot be empty")
    @Size(max = 100, message = "Cannot process more than 100 lunch logs at once")
    @Valid
    private List<CreateLunchLogRequest> lunchLogs;
}