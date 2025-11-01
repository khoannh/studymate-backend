package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.admin.AdminStatisticsResponse;
import exe201.studymatebackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<AdminStatisticsResponse> getStatistics() {
        var result = adminService.getDashboardStats();
        return ApiResponse.<AdminStatisticsResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thống kê tổng quát thành công")
                .result(result)
                .build();
    }

    @GetMapping("/statistics/week/{week}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<Map<String, Long>> getStatisticsByWeek(@PathVariable int week) {
        var result = adminService.getRegistrationsForSpecificWeek(week);
        return ApiResponse.<Map<String, Long>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thống kê theo tuần " + week + " thành công")
                .result(result)
                .build();
    }

    @GetMapping("/statistics/month/{month}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<Map<String, Long>> getStatisticsByMonth(@PathVariable int month) {
        var result = adminService.getRegistrationsForSpecificMonth(month);
        return ApiResponse.<Map<String, Long>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thống kê theo tháng " + month + " thành công")
                .result(result)
                .build();
    }
}
