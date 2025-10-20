package exe201.studymatebackend.dto.request.report;

import exe201.studymatebackend.pojo.ReportStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReportRequest {
    private String content;
    private ReportStatus status;
}