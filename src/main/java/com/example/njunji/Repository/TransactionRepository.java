package com.example.njunji.Repository;

import com.example.njunji.Entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @Query(value = "SELECT * FROM transaction_entity u WHERE u.payer_transactionid=?1",nativeQuery = true)
    TransactionEntity findByPayerTransactionID(String payerId);
}
