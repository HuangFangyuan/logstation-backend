package com.hfy.elasticsearch.service.interfaces;

/**
 * Created by HuangFangyuan on 2018/3/16.
 */
public interface MailService {
    void sendSimpleEmail(String from, String to, String subject, String content);
}
