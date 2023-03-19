package com.rkv.trackmail.service;

import com.rkv.trackmail.config.EmailServer;
import com.rkv.trackmail.dto.EmailRequest;
import com.rkv.trackmail.dto.StandardResponse;
import com.rkv.trackmail.entity.EmailAccount;
import com.rkv.trackmail.entity.EmailRecipient;
import com.rkv.trackmail.exception.CustomException;
import com.rkv.trackmail.exception.GatewayException;
import com.rkv.trackmail.repository.EmailServerRepository;
import com.rkv.trackmail.repository.EmailRecipientRepository;
import com.rkv.trackmail.util.EmailUtil;
import jakarta.annotation.PostConstruct;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {

    private static Properties prop = new Properties();

    @Autowired
    private EmailServer emailServer;

    @Autowired
    private EmailRecipientRepository emailRecipientRepository;

    @Autowired
    private EmailServerRepository emailServerRepository;

    @PostConstruct
    public void init() {
        prop.put("mail.smtp.auth", emailServer.isAuth());
        prop.put("mail.smtp.host", emailServer.getHost());
        prop.put("mail.smtp.port", emailServer.getPort());
        prop.put("mail.smtp.ssl.enable", emailServer.isSsl());
        prop.put("mail.smtp.ssl.protocols", emailServer.getProtocol());
        prop.put("mail.smtp.socketFactory.class", emailServer.getSocket());
        prop.put("mail.smtp.socketFactory.port", emailServer.getPort());
    }


    public StandardResponse sendEmail(EmailRequest request) {
        if (!EmailUtil.isEmailAddressValid(request.toAddress())) {
            throw new CustomException("email address is not acceptable");
        }
        CompletableFuture.runAsync(() -> processEmailCommunication(request));
        return new StandardResponse(true);
    }

    private void processEmailCommunication(EmailRequest request) {
        try {
            String pixelId = request.toAddress().split("@")[0] + System.currentTimeMillis();
            EmailRecipient rec = new EmailRecipient(pixelId, request.toAddress(), request.subject(), LocalDate.now());
            emailRecipientRepository.save(rec);

            Optional<EmailAccount> emailAcctOptional = emailServerRepository.findById("admin");
            EmailAccount emailAcct = emailAcctOptional.get();
            if (null == emailAcct) {
                throw new GatewayException("Attempt to fetch server details failed");
            }

            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailAcct.getEmailId(), emailAcct.getAppPwd());
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAcct.getEmailId()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.toAddress()));
            message.setSubject(request.subject());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            //add image tracker here
            mimeBodyPart.setContent(request.content(), "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            try {
                Transport.send(message);
            } catch (MessagingException ex) {
                EmailRecipient recipient = emailRecipientRepository.findById(pixelId).get();
                recipient.setFailedToSend(true);
                emailRecipientRepository.save(recipient);
            }
        } catch (Exception ex) {
            //TODO add logs
            System.out.println(ex);
            throw new GatewayException("Failed to process Email, Try Again!");
        }
    }
}
