package exe201.studymatebackend.dto.request.report;

import lombok.Data;

@Data
public class CreateReportRequest {
    private String reportedUsername; // tên tài khoản bị report
    private String content;          // lý do báo cáo
}