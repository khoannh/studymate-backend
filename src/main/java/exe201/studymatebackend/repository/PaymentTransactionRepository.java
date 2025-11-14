package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {
    PaymentTransaction findByOrderCode(Long orderCode);
}
