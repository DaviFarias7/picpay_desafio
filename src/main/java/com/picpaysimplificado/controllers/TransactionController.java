package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    @Autowired
    public TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        Transaction newTransaction = this.transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }

    @GetMapping
    public  ResponseEntity<List<Transaction>> getTransactions(){
        List<Transaction> transactions = transactionService.getTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
