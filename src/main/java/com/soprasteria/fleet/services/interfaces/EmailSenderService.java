package com.soprasteria.fleet.services.interfaces;

import java.util.List;

public interface EmailSenderService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendMessageWithAttachments(String to, String subject, String text, List<String> attachments);
}
