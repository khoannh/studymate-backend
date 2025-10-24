package exe201.studymatebackend.dto.request.studyschedule;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyScheduleRequest {
    
    @NotBlank(message = "Tên buổi học không thể để trống")
    private String title;

    private String description;

    @NotBlank(message = "Link buổi học không thể để trống")
    private String meetingLink;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    @Future(message = "Thời gian bắt đầu không hợp lệ")
    private LocalDateTime startTime;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    private LocalDateTime endTime;

}
