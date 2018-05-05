package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.entity.Monitor;

public interface MailService {
    void sendEmail(Monitor m, Object message);
    void sendSimpleEmail(String from, String to, String subject, String content);
}
