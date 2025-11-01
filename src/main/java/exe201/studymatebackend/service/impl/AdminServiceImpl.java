package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.response.admin.AdminStatisticsResponse;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.repository.RoomRepository;
import exe201.studymatebackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public AdminStatisticsResponse getDashboardStats() {

        Map<String, Long> userByMonth = convertToMap(accountRepository.countRegistrationsByMonth(), "Tháng");
        Map<String, Long> roomByMonth = convertToMap(roomRepository.countRoomsByMonth(), "Tháng");

        Map<String, Long> userByWeek = convertToMap(accountRepository.countRegistrationsByWeek(), "Tuần");
        Map<String, Long> roomByWeek = convertToMap(roomRepository.countRoomsByWeek(), "Tuần");

        long totalUsers = accountRepository.count();
        long totalRooms = roomRepository.count();

        double userGrowth = calculateMonthGrowth(userByMonth);
        double roomGrowth = calculateMonthGrowth(roomByMonth);

        return AdminStatisticsResponse.builder()
                .userRegistrationsByMonth(userByMonth)
                .roomCreationsByMonth(roomByMonth)
                .userRegistrationsByWeek(userByWeek)
                .roomCreationsByWeek(roomByWeek)
                .totalUsers(totalUsers)
                .totalRooms(totalRooms)
                .userGrowthRate(userGrowth)
                .roomGrowthRate(roomGrowth)
                .build();
    }

    private Map<String, Long> convertToMap(List<Object[]> list, String prefix) {
        Map<String, Long> map = new HashMap<>();
        for (Object[] obj : list) {
            int key = ((Number) obj[0]).intValue();
            long count = ((Number) obj[1]).longValue();
            map.put(prefix + " " + key, count);
        }
        return map;
    }

    private double calculateMonthGrowth(Map<String, Long> data) {
        if (data.size() < 2) return 0.0;
        int currentMonth = LocalDate.now().getMonthValue();
        long current = data.getOrDefault("Tháng " + currentMonth, 0L);
        long previous = data.getOrDefault("Tháng " + (currentMonth - 1), 0L);
        if (previous == 0) return 100.0;
        return ((double) (current - previous) / previous) * 100.0;
    }

    @Override
    public Map<String, Long> getRegistrationsForSpecificWeek(int week) {
        Map<String, Long> result = new HashMap<>();
        result.put("Người dùng", accountRepository.countRegistrationsInWeek(week));
        result.put("Phòng", roomRepository.countRoomsInWeek(week));
        return result;
    }

    @Override
    public Map<String, Long> getRegistrationsForSpecificMonth(int month) {
        Map<String, Long> result = new HashMap<>();
        result.put("Người dùng", accountRepository.countRegistrationsInMonth(month));
        result.put("Phòng", roomRepository.countRoomsInMonth(month));
        return result;
    }
}
