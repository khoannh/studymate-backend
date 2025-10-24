package exe201.studymatebackend.dto.response.studyschedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllStudyScheduleOfRoomResponse {
    private Integer roomID;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLastPage;
    private List<ScheduleResponse> schedules;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ScheduleResponse {
        private Integer scheduleID;
        private String title;
        private String description;
        private String meetingLink;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private LocalDateTime createdAt;
        private Boolean notified;
        private String status;
    }

}
