package com.emaratech.custody.hardwarecustody.service;

import java.util.Map;

public interface IEmailService {
    void sendEmail(String to, String subject, String text, Map<String, Object> model);
}
