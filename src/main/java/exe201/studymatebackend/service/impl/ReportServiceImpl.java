package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.report.UpdateReportRequest;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.Report;
import exe201.studymatebackend.pojo.ReportStatus;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.repository.ReportRepository;
import exe201.studymatebackend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Report> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) {
            throw new AppException(ErrorCode.REPORT_NOT_FOUND);
        }
        return reports;
    }

    @Override
    public Report getReportById(Integer id) {
        Report report = reportRepository.findById(id)
                .orElse(null);
        if (report == null) {
            throw new AppException(ErrorCode.REPORT_NOT_FOUND);
        }
        return report;
    }

    @Override
    public Report createReport(String senderUsername, String reportedUsername, String content, MultipartFile evidence) {
        Account sender = accountRepository.findByUsername(senderUsername);
        if (sender == null) {
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        }

        Account reported = accountRepository.findByUsername(reportedUsername);
        if (reported == null) {
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        }

        Report report = new Report();
        report.setSender(sender);
        report.setReported(reported);
        report.setContent(content);

        if (evidence != null && !evidence.isEmpty()) {
            try {
                report.setEvidence(evidence.getBytes());
            } catch (IOException e) {
                throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }

        report.setStatus(ReportStatus.PENDING); // 👈 mặc định là chờ xử lý
        report.setCreatedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());

        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(Integer id, UpdateReportRequest request) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));


        if (request.getContent() != null) {
            report.setContent(request.getContent());
        }


        if (request.getStatus() != null) {
            report.setStatus(request.getStatus());
        }

        report.setUpdatedAt(LocalDateTime.now());
        return reportRepository.save(report);
    }

    @Override
    public void deleteReport(Integer id) {
        if (!reportRepository.existsById(id)) {
            throw new AppException(ErrorCode.REPORT_NOT_FOUND);
        }
        reportRepository.deleteById(id);
    }
}