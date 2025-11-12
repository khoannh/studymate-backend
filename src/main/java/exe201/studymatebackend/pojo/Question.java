package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionID;

    @Column(nullable = false)
    private String question;

    @Column(length = 1000)
    private String options;


    @Column(nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "quizset_id")
    private QuizSet quizset;

}
