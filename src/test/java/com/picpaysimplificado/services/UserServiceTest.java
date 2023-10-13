package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {


    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Davi";
    public static final String LASTNAME = "Farias";
    public static final String DOCUMENT = "99988833388";
    public static final String EMAIL = "davi@gmail.com";
    public static final String PASSWORD = "123456";
    public static final BigDecimal BALANCE = new BigDecimal(1000);

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private User user;
    private UserDTO userDTO;
    private UserType userType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void validateTransaction_insufficientBalance_throwsException() {
        User sender = new User();
        sender.setBalance(BigDecimal.valueOf(100));  // Assuming sender balance is 100
        BigDecimal amount = BigDecimal.valueOf(200);

        Exception exception = assertThrows(Exception.class, () ->
                service.validateTransaction(sender, amount));

        // Verify the exception message
        String expectedMessage = "Saldo insuficiente";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));
    }

    @Test
    void validateTransaction_merchantUser_throwsException() {
        User sender = new User();
        sender.setUserTYpe(UserType.MERCHANT);
        BigDecimal amount = BigDecimal.valueOf(50);

        Exception exception = assertThrows(Exception.class, () ->
                service.validateTransaction(sender, amount));

        // Verify the exception message
        String expectedMessage = "O usuário do tipo lojista não está autorizado a realizar transação";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));
    }
    @Test
    void whenFindUserByIdReturnAnUserInstace() throws Exception {
        when(repository.findUserById(ID)).thenReturn(Optional.ofNullable(user));

        User response = service.findUserById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(FIRST_NAME, response.getFirstName());
        assertEquals(LASTNAME, response.getLastName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(BALANCE, response.getBalance());
    }

    @Test
    void whenCreateUserThenReturnSuccess() {

        when(repository.save(any(User.class))).thenReturn(user);

        User response = service.createUser(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(FIRST_NAME, response.getFirstName());
        assertEquals(LASTNAME, response.getLastName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(BALANCE, response.getBalance());

        verify(repository).save(any(User.class));
    }

    @Test
    void whenGetUsersThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.getAllUsers();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(0).getClass());

        assertEquals(ID, response.get(0).getId());
        assertEquals(FIRST_NAME, response.get(0).getFirstName());
        assertEquals(LASTNAME, response.get(0).getLastName());
        assertEquals(DOCUMENT, response.get(0).getDocument());
        assertEquals(EMAIL, response.get(0).getEmail());
        assertEquals(PASSWORD, response.get(0).getPassword());
        assertEquals(BALANCE, response.get(0).getBalance());
        assertEquals(UserType.COMMON, response.get(0).getUserTYpe());
    }

    private void startUser(){
        user = new User(ID, FIRST_NAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, userType.COMMON);
        userDTO = new UserDTO(FIRST_NAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, userType.COMMON);
    }
}