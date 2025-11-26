package exe201.studymatebackend.dto.response.admin;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Builder
@Data
public class AdminStatisticsResponse {
    private Map<String, Long> userRegistrationsByMonth;
    private Map<String, Long> roomCreationsByMonth;
    private Map<String, Long> userRegistrationsByWeek;
    private Map<String, Long> roomCreationsByWeek;
    private long totalUsers;
    private long totalRooms;
    private long totalRevenue;
    private double userGrowthRate;
    private double roomGrowthRate;
    private long totalPaidTransactions;
}
