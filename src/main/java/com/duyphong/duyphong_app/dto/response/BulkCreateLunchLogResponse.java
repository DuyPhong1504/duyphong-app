package com.duyphong.duyphong_app.dto.response;

import lombok.*;

import java.util.List;

/**
 * Response DTO for bulk lunch log registration
 * Returns the list of created lunch log entries
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class BulkCreateLunchLogResponse {

    private int totalCreated;
    private List<LunchLogResponse> lunchLogs;
    private String message;
}