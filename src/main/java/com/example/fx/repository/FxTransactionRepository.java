package com.example.fx.repository;

import com.example.fx.entity.FxTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FxTransactionRepository extends JpaRepository<FxTransaction, Long> {
    List<FxTransaction> findByType(String type);
}