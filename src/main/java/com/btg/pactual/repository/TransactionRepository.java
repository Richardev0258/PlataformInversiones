package com.btg.pactual.repository;

import com.btg.pactual.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByClientIdOrderByDateDesc(Long clientId);
}
