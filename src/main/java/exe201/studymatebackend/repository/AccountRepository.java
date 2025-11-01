package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);

    Account findByUsername(String username);

    Account findByAccountID(Integer id);

    Page<Account> findAll(Pageable pageable);

    @Query("SELECT EXTRACT(MONTH FROM a.createdAt), COUNT(a.accountID) FROM Account a GROUP BY EXTRACT(MONTH FROM a.createdAt)")
    List<Object[]> countRegistrationsByMonth();

    @Query(value = """
        SELECT EXTRACT(WEEK FROM a.created_at), COUNT(a.accountid)
        FROM account a
        GROUP BY EXTRACT(WEEK FROM a.created_at)
    """, nativeQuery = true)
    List<Object[]> countRegistrationsByWeek();

    @Query(value = "SELECT COUNT(*) FROM account WHERE EXTRACT(WEEK FROM created_at) = :week", nativeQuery = true)
    Long countRegistrationsInWeek(@Param("week") int week);

    @Query(value = "SELECT COUNT(*) FROM account WHERE EXTRACT(MONTH FROM created_at) = :month", nativeQuery = true)
    Long countRegistrationsInMonth(@Param("month") int month);

}
