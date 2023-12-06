package com.example.fx.service;

import com.example.fx.dto.FxTransactionDTO;
import com.example.fx.exception.InvalidTransactionDataException;
import com.example.fx.exception.TransactionException;
import com.example.fx.exception.TransactionNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FxService {
    FxTransactionDTO processTransaction(FxTransactionDTO dto) throws TransactionException, InvalidTransactionDataException;
    public Page<FxTransactionDTO> getAllTransactions(Pageable pageable);
    FxTransactionDTO getTransactionById(Long Id) throws TransactionNotFoundException;
    List<FxTransactionDTO> getAllBoughtTransactions();
    List<FxTransactionDTO> getAllSoldTransactions();
}
