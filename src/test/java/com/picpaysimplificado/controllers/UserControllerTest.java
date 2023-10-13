package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Davi";
    public static final String LASTNAME = "Farias";
    public static final String DOCUMENT = "99988833388";
    public static final String EMAIL = "davi@gmail.com";
    public static final String PASSWORD = "123456";
    public static final BigDecimal BALANCE = new BigDecimal(1000);

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private User user;
    private UserDTO userDTO;
    private UserType userType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenCreateUserThenReturnCreated() {
        when(service.createUser(any())).thenReturn(user);

        ResponseEntity<User> response = controller.createUser(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(service.getAllUsers()).thenReturn(userList);

        ResponseEntity<List<User>> response = controller.getAllUsers();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
    }

    private void startUser(){
        user = new User(ID, FIRST_NAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, userType.COMMON);
        userDTO = new UserDTO(FIRST_NAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, userType.COMMON);
    }
}