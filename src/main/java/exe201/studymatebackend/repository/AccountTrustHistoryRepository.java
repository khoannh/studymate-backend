package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.AccountTrustHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTrustHistoryRepository extends JpaRepository<AccountTrustHistory, Long> {
    List<AccountTrustHistory> findByAccount_AccountIDOrderByCreatedAtDesc(Integer accountId);
}
