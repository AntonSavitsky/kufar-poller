package kufar.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Data
@AllArgsConstructor
public class EmailService {

  private PropertyService propertyService;

  public EmailService() {
    propertyService = new PropertyService();
  }

  public void sendEmail(String recipientEmail, String text) {
    final String senderEmail = propertyService.getProperty("EMAIL");
    final String senderPassword = propertyService.getProperty("EMAIL_PASSWORD");

    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true"); //TLS

    Session session = Session.getInstance(prop,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
          }
        });

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(senderEmail));
      message.setRecipients(
          Message.RecipientType.TO,
          InternetAddress.parse(recipientEmail)
      );
      message.setSubject("Новые объявления о квартирах на Kufar.by", "UTF8");
      message.setText(text);

      Transport.send(message);

      System.out.println("Sent email.");
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

}