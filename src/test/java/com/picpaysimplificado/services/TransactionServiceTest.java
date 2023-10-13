package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    public static final Long ID = 1L;
    public static final Long ID_R = 2L;
    public static final String FIRST_NAME = "Davi";
    public static final String FIRST_NAME_R = "Kessia";
    public static final String LASTNAME = "Farias";
    public static final String LASTNAME_R = "Farias";
    public static final String DOCUMENT = "99988833388";
    public static final String DOCUMENT_R = "77788833388";
    public static final String EMAIL = "davi@gmail.com";
    public static final String EMAIL_R = "kessia@gmail.com";
    public static final String PASSWORD = "123456";
    public static final String PASSWORD_R = "2345678";
    public static final BigDecimal BALANCE = new BigDecimal(1000);
    public static final BigDecimal BALANCE_R = new BigDecimal(1000);
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private NotificationService notificationService;

    private User sender;
    private User receiver;

    private UserType userType;
    private TransactionDTO validTransactionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sender = new User(ID, FIRST_NAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, userType.COMMON);
        receiver = new User(ID_R, FIRST_NAME_R, LASTNAME_R, DOCUMENT_R, EMAIL_R, PASSWORD_R, BALANCE_R, userType.COMMON);
        validTransactionDTO = new TransactionDTO(new BigDecimal(1000), ID, ID_R);
    }

    @Test
    void createTransaction_UnauthorizedTransaction_ThrowsException() throws Exception {
        when(userService.findUserById(ID)).thenReturn(sender);
        when(userService.findUserById(ID_R)).thenReturn(receiver);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(Map.of("message", "Não Autorizado"), HttpStatus.OK));

        Exception exception = assertThrows(Exception.class, () -> transactionService.createTransaction(validTransactionDTO));
        assertEquals("Transação não autorizada", exception.getMessage());
    }

    @Test
    void createTransaction_AuthorizationFailed_ThrowsException() throws Exception {
        BigDecimal amount = BigDecimal.valueOf(100);
        when(userService.findUserById(ID)).thenReturn(sender);
        when(userService.findUserById(ID_R)).thenReturn(receiver);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(new HashMap<>(), HttpStatus.INTERNAL_SERVER_ERROR));

        Exception exception = assertThrows(Exception.class, () -> transactionService.createTransaction(validTransactionDTO));
        assertEquals("Transação não autorizada", exception.getMessage());
    }
}