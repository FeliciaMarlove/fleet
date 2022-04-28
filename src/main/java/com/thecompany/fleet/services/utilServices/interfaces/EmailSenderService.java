package com.thecompany.fleet.services.utilServices.interfaces;

public interface EmailSenderService {
    void sendSimpleMessage(String to, String subject, String text);
}
