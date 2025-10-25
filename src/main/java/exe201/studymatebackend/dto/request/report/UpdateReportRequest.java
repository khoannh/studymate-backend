package exe201.studymatebackend.dto.request.report;

import exe201.studymatebackend.enums.ReportStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReportRequest {
    private String content;
    private ReportStatus status;
}