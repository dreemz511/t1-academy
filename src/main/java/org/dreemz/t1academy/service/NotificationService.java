package org.dreemz.t1academy.service;

import org.dreemz.t1academy.dto.KafkaTaskDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Value("${spring.mail.username}")
    private String toAddress;

    @Value("${spring.mail.username}")
    private String fromAddress;

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail(KafkaTaskDto dto) {
        String message = "Task status with id `%d` was changed to `%s`".formatted(dto.id(), dto.status());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject("Task status is changed");
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);

    }
}
