package com.example.fx.service.mapper;

import com.example.fx.dto.FxTransactionDTO;
import com.example.fx.entity.FxTransaction;
import com.example.fx.exception.TransactionException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FxTransactionMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public FxTransactionDTO convertToDto(FxTransaction transaction) {
        return modelMapper.map(transaction, FxTransactionDTO.class);
    }

    public FxTransaction convertToEntity(FxTransactionDTO dto) throws TransactionException {
        return modelMapper.map(dto, FxTransaction.class);
    }
}
