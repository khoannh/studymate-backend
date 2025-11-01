package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.response.admin.AdminStatisticsResponse;

import java.util.Map;

public interface AdminService {

    AdminStatisticsResponse getDashboardStats();

    Map<String, Long> getRegistrationsForSpecificWeek(int week);

    Map<String, Long> getRegistrationsForSpecificMonth(int month);
}
