package com.example.fx.service;

import com.example.fx.dto.FxTransactionDTO;
import com.example.fx.entity.FxTransaction;
import com.example.fx.repository.FxTransactionRepository;
import com.example.fx.service.mapper.FxTransactionMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FxServiceTest {

    @Mock
    private FxTransactionRepository fxTransactionRepository;

    @Mock
    FxTransactionMapper mapper;
    private FxService testObject;

    @BeforeEach
    public void setUp() {
        testObject = new FxServiceImpl(fxTransactionRepository, mapper);
    }

    @Test
    public void whenFindAllTransactions_thenReturnsPage() {
        //Given
        FxTransaction transaction = new FxTransaction(1L, "USD", new BigDecimal("100.00"), "BUY");
        List<FxTransaction> transactions = List.of(transaction);
        Page<FxTransaction> page = new PageImpl<>(transactions);

        //When
        when(fxTransactionRepository.findAll(any(PageRequest.class))).thenReturn(page);
        FxTransactionDTO dto = new FxTransactionDTO(1L, "USD", new BigDecimal("100.00"), "BUY");
        when(mapper.convertToDto(any(FxTransaction.class))).thenReturn(dto);

        //Then
        Page<FxTransactionDTO> returnedPage = testObject.getAllTransactions(PageRequest.of(0, 10));

        assertEquals(1, returnedPage.getContent().size());
        FxTransactionDTO result = returnedPage.getContent().get(0);
        assertEquals("USD", result.getCurrency());
        assertEquals(new BigDecimal("100.00"), result.getAmount());
        assertEquals("BUY", result.getType());
    }
}

