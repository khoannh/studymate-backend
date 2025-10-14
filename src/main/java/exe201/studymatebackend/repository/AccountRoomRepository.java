package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.AccountRoom;
import exe201.studymatebackend.pojo.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoomRepository extends JpaRepository<AccountRoom, Integer> {
    AccountRoom findAccountRoomByAccountAndRoom(Account account, Room room);

    Page<AccountRoom> findAllByAccount(Account account, Pageable pageable);


}
