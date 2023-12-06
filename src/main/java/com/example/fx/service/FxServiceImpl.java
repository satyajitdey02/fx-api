package com.example.fx.service;

import com.example.fx.dto.FxTransactionDTO;
import com.example.fx.entity.FxTransaction;
import com.example.fx.exception.InvalidTransactionDataException;
import com.example.fx.exception.TransactionException;
import com.example.fx.exception.TransactionNotFoundException;
import com.example.fx.repository.FxTransactionRepository;
import com.example.fx.service.mapper.FxTransactionMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FxServiceImpl implements FxService {
    private final FxTransactionRepository repository;
    private final FxTransactionMapper mapper;

    public FxServiceImpl(FxTransactionRepository repository, FxTransactionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public FxTransactionDTO processTransaction(FxTransactionDTO dto)
            throws TransactionException, InvalidTransactionDataException {
        if (dto == null) {
            throw new InvalidTransactionDataException();
        }

        FxTransaction transaction = mapper.convertToEntity(dto);
        transaction = repository.save(transaction);

        if (transaction.getId() == null || transaction.getId() < 1) {
            throw new TransactionException("Transaction is not created.");
        }

        return mapper.convertToDto(transaction);
    }

    @Override
    public Page<FxTransactionDTO> getAllTransactions(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::convertToDto);
    }

    @Override
    public FxTransactionDTO getTransactionById(Long id) throws TransactionNotFoundException {
        Optional<FxTransaction> fxTransactionOpt = repository.findById(id);
        if (fxTransactionOpt.isEmpty()) {
            throw new TransactionNotFoundException();
        }

        return fxTransactionOpt.map(mapper::convertToDto).orElse(null);
    }

    @Override
    public List<FxTransactionDTO> getAllBoughtTransactions() {
        return repository.findByType("BUY").stream()
                .map(mapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<FxTransactionDTO> getAllSoldTransactions() {
        return repository.findByType("SELL").stream()
                .map(mapper::convertToDto).collect(Collectors.toList());
    }


}

