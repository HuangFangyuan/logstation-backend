package com.hfy.logstation.service.impl;

import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.service.interfaces.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(Monitor m, Object message) {
        sendSimpleEmail(
                "hfy_1996@163.com",
                m.getContact(),
                m.getSubject(),
                m.getContent() + message);
    }

    @Override
    public void sendSimpleEmail(String from, String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
