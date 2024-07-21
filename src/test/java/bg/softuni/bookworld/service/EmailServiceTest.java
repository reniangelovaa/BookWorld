package bg.softuni.bookworld.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {


    private EmailService emailService;
    private JavaMailSender emailSender;

    @BeforeEach
    public void setUp() {
        emailSender = Mockito.mock(JavaMailSender.class);
        emailService = new EmailService(emailSender);
    }

    @Test
    public void testSendSimpleMessage() {
        // Given
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Text";

        // When
        emailService.sendSimpleMessage(to, subject, text);

        // Then
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(emailSender).send(messageCaptor.capture());
        SimpleMailMessage capturedMessage = messageCaptor.getValue();

        assertEquals(to, capturedMessage.getTo()[0]);
        assertEquals(subject, capturedMessage.getSubject());
        assertEquals(text, capturedMessage.getText());
    }
}
