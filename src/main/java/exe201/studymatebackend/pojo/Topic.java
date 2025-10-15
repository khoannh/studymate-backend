package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topics")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public Topic(String topicName, String description, boolean isActive) {
        this.topicName = topicName;
        this.description = description;
        this.isActive = isActive;
    }
}
