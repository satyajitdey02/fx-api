package com.example.fx.controller;

import com.example.fx.dto.FxTransactionDTO;
import com.example.fx.exception.InvalidTransactionDataException;
import com.example.fx.exception.TransactionException;
import com.example.fx.exception.TransactionNotFoundException;
import com.example.fx.service.FxService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fx-api/tx")
public class FxController {
    final FxService fxService;

    public FxController(FxService fxService) {
        this.fxService = fxService;
    }

    @PostMapping("/buy")
    public ResponseEntity<FxTransactionDTO> buyCurrency(@RequestBody FxTransactionDTO transactionDTO)
            throws TransactionException, InvalidTransactionDataException {
        transactionDTO.setType("BUY");
        FxTransactionDTO dto = fxService.processTransaction(transactionDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/buy")
    public ResponseEntity<List<FxTransactionDTO>> getAllBoughtTransactions() {
        return ResponseEntity.ok(fxService.getAllBoughtTransactions());
    }

    @PostMapping("/sell")
    public ResponseEntity<FxTransactionDTO> sellCurrency(@RequestBody FxTransactionDTO transactionDTO)
            throws TransactionException, InvalidTransactionDataException {
        transactionDTO.setType("SELL");
        FxTransactionDTO dto = fxService.processTransaction(transactionDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/sell")
    public ResponseEntity<List<FxTransactionDTO>> getAllSoldTransactions() {
        return ResponseEntity.ok(fxService.getAllSoldTransactions());
    }

    @GetMapping("/{txId}")
    public ResponseEntity<FxTransactionDTO> getTransactionById(@PathVariable("txId") Long txId)
            throws TransactionNotFoundException {
        return ResponseEntity.ok(fxService.getTransactionById(txId));
    }

    @GetMapping
    public ResponseEntity<Page<FxTransactionDTO>> getAllTransactions(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Page<FxTransactionDTO> transactions = fxService.getAllTransactions(PageRequest.of(page, size));
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
