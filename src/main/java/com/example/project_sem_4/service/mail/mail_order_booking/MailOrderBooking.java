package com.example.project_sem_4.service.mail.mail_order_booking;

import com.example.project_sem_4.service.mail.mail_comfirm.ThymeleafMailConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailOrderBooking {
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";

    @Value("${config.mail.host}")
    private String host;
    @Value("${config.mail.port}")
    private String port;
    @Value("${config.mail.username}")
    private String email;
    @Value("${config.mail.password}")
    private String password;

    @Autowired
    private ThymeleafMailOrderBookingService thymeleafMailOrderBookingService;

    public void sendMailOrderBooking(String name,String emailOrderBooking,String hour) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(emailOrderBooking)});

            message.setFrom(new InternetAddress(email));
            message.setSubject("Hair Style xin báo sắp đến giờ cắt tóc");
            message.setContent(thymeleafMailOrderBookingService.getContent(name,hour), CONTENT_TYPE_TEXT_HTML);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
