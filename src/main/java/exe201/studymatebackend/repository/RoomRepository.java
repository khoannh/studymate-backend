package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room findByRoomName(String roomName);

    Room findByRoomID(Integer roomID);

    Page<Room> findAll(Pageable pageable);

    @Query("SELECT EXTRACT(MONTH FROM r.createdAt), COUNT(r.roomID) FROM Room r GROUP BY EXTRACT(MONTH FROM r.createdAt)")
    List<Object[]> countRoomsByMonth();

    @Query(value = """
        SELECT EXTRACT(WEEK FROM r.created_at), COUNT(r.roomid)
        FROM room r
        GROUP BY EXTRACT(WEEK FROM r.created_at)
    """, nativeQuery = true)
    List<Object[]> countRoomsByWeek();

    @Query(value = "SELECT COUNT(*) FROM room WHERE EXTRACT(WEEK FROM created_at) = :week", nativeQuery = true)
    Long countRoomsInWeek(@Param("week") int week);

    @Query(value = "SELECT COUNT(*) FROM room WHERE EXTRACT(MONTH FROM created_at) = :month", nativeQuery = true)
    Long countRoomsInMonth(@Param("month") int month);
}
