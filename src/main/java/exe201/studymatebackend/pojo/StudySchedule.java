package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleID;

    @Column(nullable = false, length = 100)
    private String title;

    private String description;

    @Column(nullable = false, length = 100)
    private String meetingLink;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean notified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // 🔹 TRẠNG THÁI ĐỘNG (không lưu vào DB)
    @Transient
    public String getStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) {
            return "Chưa bắt đầu";
        } else if (now.isAfter(endTime)) {
            return "Đã kết thúc";
        } else {
            return "Đang diễn ra";
        }
    }
}