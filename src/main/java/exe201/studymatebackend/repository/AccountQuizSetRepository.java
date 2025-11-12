package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.AccountQuizSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountQuizSetRepository extends JpaRepository<AccountQuizSet, Integer> {
}
