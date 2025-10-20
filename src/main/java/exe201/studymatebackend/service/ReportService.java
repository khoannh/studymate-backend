package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.report.UpdateReportRequest;
import exe201.studymatebackend.pojo.Report;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReportService {
    List<Report> getAllReports();
    Report getReportById(Integer id);
    Report createReport(String senderUsername, String reportedUsername, String content, MultipartFile evidence);
    Report updateReport(Integer id, UpdateReportRequest request);
    void deleteReport(Integer id);
}
