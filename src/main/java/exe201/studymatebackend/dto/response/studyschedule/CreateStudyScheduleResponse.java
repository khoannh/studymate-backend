package exe201.studymatebackend.dto.response.studyschedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateStudyScheduleResponse {
    private Integer scheduleID;
    private String title;
    private String description;
    private String meetingLink;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private Boolean notified;
    private String status;
    private Integer roomID;
}
