package com.soprasteria.fleet.services.utilServices.interfaces;

import java.util.List;

public interface EmailSenderService {
    void sendSimpleMessage(String to, String subject, String text);
}
