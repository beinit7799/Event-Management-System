package com.EventManagementSystem.EventManagementSystem.service;

import com.EventManagementSystem.EventManagementSystem.model.User;
import com.EventManagementSystem.EventManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmailSenderService {


    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    
    public EmailSenderService(JavaMailSender mailSender, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }



    @Value("${spring.mail.username}")
    private String fromEmail;


    public void sendEmail(String subject, String body) {
        List<User> users = userRepository.findAll(); // Fetch all users
        for (User user : users) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(user.getEmail()); // Set the user's email address
            message.setText(body);
            message.setSubject(subject);

            mailSender.send(message); // Send email to each user
        }
    }
}