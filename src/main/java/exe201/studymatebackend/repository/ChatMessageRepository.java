package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.ChatMessage;
import exe201.studymatebackend.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByRoomOrderBySentAtAsc(Room room);
}
