package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);

    Account findByUsername(String username);

    Account findByAccountID(Integer id);

    Page<Account> findAll(Pageable pageable);

//    List<Account> findAll(Pageable pageable);
}
