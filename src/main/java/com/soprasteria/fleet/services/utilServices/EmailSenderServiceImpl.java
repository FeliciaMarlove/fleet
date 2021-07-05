package com.soprasteria.fleet.services.utilServices;

import com.soprasteria.fleet.services.utilServices.interfaces.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Properties;

@Component
public final class EmailSenderServiceImpl implements EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;
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
        Session session = setSession(to, subject, text);
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

    /*
    TODO ? List of attachments might be of type byte[] according to storage chosen for files
     */
    @Override
    public void sendMessageWithAttachments(String to, String subject, String text, List<String> attachments) {
        Session session = setSession(to, subject, text);setSession(to, subject, text);
        try {

            MimeMessage message = new MimeMessage(session);

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            for (String pathToAttachment: attachments) {
                FileSystemResource file
                        = new FileSystemResource(new File(pathToAttachment));
                helper.addAttachment(file.getFilename(), file);
            }

            // emailSender.send(message); // if next line not working try this

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Session setSession(String to, String subject, String text) {
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }
}
