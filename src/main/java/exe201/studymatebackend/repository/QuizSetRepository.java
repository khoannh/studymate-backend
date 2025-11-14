package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.QuizSet;
import exe201.studymatebackend.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuizSetRepository extends JpaRepository<QuizSet, Integer> {

    List<QuizSet> findAllByExpiredAtBefore(LocalDateTime now);

    List<QuizSet> findAllByRoomOrderByCreatedAtDesc(Room room);

    QuizSet findByQuizSetID(Integer quizSetID);
}
