package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizSetID;

    @Column(unique = false, nullable = false)
    private String quizSetName;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Column(nullable = false)
    private Integer duration; // phút

    @Column(nullable = false)
    private Integer totalQuestions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToMany(mappedBy = "quizset", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AccountQuizSet> accountQuizSetList;

    @OneToMany(mappedBy = "quizset", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questionList;


}
