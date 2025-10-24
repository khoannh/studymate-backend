package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.Room;
import exe201.studymatebackend.pojo.StudySchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Integer> {

    Page<StudySchedule> findByRoom(Room room, Pageable pageable);

    List<StudySchedule> findByNotifiedFalseAndStartTimeBetween(LocalDateTime now, LocalDateTime targetTime);
}
