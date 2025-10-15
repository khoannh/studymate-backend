package exe201.studymatebackend.repository;

import exe201.studymatebackend.enums.RoomRole;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.AccountRoom;
import exe201.studymatebackend.pojo.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRoomRepository extends JpaRepository<AccountRoom, Integer> {
    AccountRoom findAccountRoomByAccountAndRoomAndLeftAtIsNull(Account account, Room room);

    Optional<AccountRoom> findByAccountAndRoom(Account account, Room room);

    Page<AccountRoom> findAllByAccountAndLeftAtIsNull(Account account, Pageable pageable);

    Integer findAccountIdByRoomAndRoomRole(Room room, RoomRole role);
}
