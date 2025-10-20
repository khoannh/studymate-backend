package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.report.UpdateReportRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.pojo.Report;
import exe201.studymatebackend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/report-management")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports")
    public ApiResponse<List<Report>> getAllReports() {
        return ApiResponse.<List<Report>>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved reports successfully")
                .result(reportService.getAllReports())
                .build();
    }

    @GetMapping("/reports/{id}")
    public ApiResponse<Report> getReportById(@PathVariable Integer id) {
        return ApiResponse.<Report>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved report successfully")
                .result(reportService.getReportById(id))
                .build();
    }

    @PostMapping(
            value = "/reports",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<Report> createReport(
            @RequestParam("reportedUsername") String reportedUsername,
            @RequestParam("content") String content,
            @RequestPart(value = "evidence", required = false) MultipartFile evidence,
            Authentication authentication
    ) {
        String currentUsername = authentication.getName();
        Report report = reportService.createReport(currentUsername, reportedUsername, content, evidence);
        return ApiResponse.<Report>builder()
                .code(HttpStatus.CREATED.value())
                .message("Created report successfully")
                .result(report)
                .build();
    }

    @DeleteMapping("/reports/{id}")
    public ApiResponse<String> deleteReport(@PathVariable Integer id) {
        reportService.deleteReport(id);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Deleted report successfully")
                .result("Deleted")
                .build();
    }
    @PutMapping(value = "/reports/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Report> updateReport(
            @PathVariable Integer id,
            @RequestBody UpdateReportRequest request
    ) {
        Report updated = reportService.updateReport(id, request);
        return ApiResponse.<Report>builder()
                .code(HttpStatus.OK.value())
                .message("Updated report successfully")
                .result(updated)
                .build();
    }
}
