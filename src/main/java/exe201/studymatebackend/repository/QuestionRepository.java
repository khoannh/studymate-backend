package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.Question;
import exe201.studymatebackend.pojo.QuizSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllByQuizset(QuizSet quizset);

}
