package com.picpaysimplificado.services;

import com.picpaysimplificado.dtos.NotificationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.picpaysimplificado.domain.user.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendNotification_NotificationSent_Success() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");

        ResponseEntity<String> responseEntity = ResponseEntity.ok("Notification sent");
        when(restTemplate.postForEntity(any(String.class), any(NotificationDTO.class), any(Class.class)))
                .thenReturn(responseEntity);

        notificationService.sendNotification(user, "Test notification");
    }

    @Test
    void sendNotification_NotificationFailed_ExceptionThrown() {
        User user = new User();
        user.setEmail("test@example.com");

        ResponseEntity<String> responseEntity = ResponseEntity.ok("Notification sent");
        when(restTemplate.postForEntity(any(String.class), any(NotificationDTO.class), any(Class.class)))
                .thenReturn(responseEntity);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
                () -> notificationService.sendNotification(user, "Test notification"));

        assertEquals("Serviço de notificação está fora do ar", exception.getMessage());
    }
}