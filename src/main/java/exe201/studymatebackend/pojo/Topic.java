package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "topics")
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topicID;

    @Column(nullable = false, unique = true, length = 100)
    private String topicName;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private boolean isActive = true; // true = ACTIVE, false = INACTIVE
}
