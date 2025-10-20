package exe201.studymatebackend.dto.response.report;

import exe201.studymatebackend.pojo.ReportStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ReportResponse {
    private Integer reportId;
    private String senderName;
    private String reportedName;
    private String content;
    private byte[] evidence;
    private ReportStatus status;
    private LocalDateTime createdAt;
}