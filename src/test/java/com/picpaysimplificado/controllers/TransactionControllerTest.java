package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.services.TransactionService;
import com.picpaysimplificado.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    private TransactionController transactionController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionController = new TransactionController();
        transactionController.transactionService = transactionService;
    }

    @Test
    void createTransaction_ValidTransaction_ReturnsOK() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(100) , 1L, 2L);
        Transaction newTransaction = new Transaction();

        when(transactionService.createTransaction(transactionDTO)).thenReturn(newTransaction);

        ResponseEntity<Transaction> responseEntity = transactionController.createTransaction(transactionDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newTransaction, responseEntity.getBody());
    }

    @Test
    void getTransactions_ReturnsTransactions_ReturnsOK() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        transactions.add(new Transaction());

        when(transactionService.getTransactions()).thenReturn(transactions);

        ResponseEntity<List<Transaction>> responseEntity = transactionController.getTransactions();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transactions, responseEntity.getBody());
    }
}