package exe201.studymatebackend.pojo;

import exe201.studymatebackend.enums.RoomRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private LocalDateTime joinedAt;

    @Column(nullable = true)
    private LocalDateTime leftAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomRole roomRole;


}
