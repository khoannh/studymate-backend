package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "reported_id", nullable = false)
    private Account reported;

    @Column(nullable = false)
    private String content;

    @Lob
    @Column(name = "evidence")
    private byte[] evidence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status; // 👈 thêm status

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
