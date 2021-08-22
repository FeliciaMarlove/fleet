package com.soprasteria.fleet.services.utilServices;

import com.soprasteria.fleet.services.utilServices.interfaces.EmailSenderService;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public final class EmailSenderServiceImpl implements EmailSenderService {
    private static final String username = "fleet.tfe.2021@gmail.com";
    private static final String password = "yabctpqummdkmeyc";
    private static final Properties props;

    static {
        props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        Session session = setSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Session setSession() {
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }
}
