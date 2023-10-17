package com.picpaysimplificado.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.picpaysimplificado.dtos.ExceptionDTO;
import com.picpaysimplificado.infra.ControllerExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void threatDuplicateEntry() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Duplicate entry");
        ResponseEntity responseEntity = controllerExceptionHandler.threatDuplicateEntry(exception);

        assertEquals(400, responseEntity.getStatusCodeValue());
        ExceptionDTO exceptionDTO = (ExceptionDTO) responseEntity.getBody();
        assertEquals("Usuário já cadastrado", exceptionDTO.message());
        assertEquals("400", exceptionDTO.statusCode());
    }

    @Test
    void threat404() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");
        ResponseEntity responseEntity = controllerExceptionHandler.threat404(exception);

        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    void threatGeneralException() {
        Exception exception = new Exception("General exception");
        ResponseEntity responseEntity = controllerExceptionHandler.threatGeneralException(exception);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ExceptionDTO exceptionDTO = (ExceptionDTO) responseEntity.getBody();
        assertEquals("General exception", exceptionDTO.message());
        assertEquals("500", exceptionDTO.statusCode());
    }
}