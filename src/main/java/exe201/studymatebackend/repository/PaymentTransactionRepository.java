package exe201.studymatebackend.repository;

import exe201.studymatebackend.enums.TransactionStatus;
import exe201.studymatebackend.pojo.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {
    PaymentTransaction findByOrderCode(Long orderCode);
    
    @Query("SELECT COALESCE(SUM(pt.amount), 0) FROM PaymentTransaction pt WHERE pt.status = :status")
    Long sumAmountByStatus(@Param("status") TransactionStatus status);
}
