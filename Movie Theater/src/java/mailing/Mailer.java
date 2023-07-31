/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mailing;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Admin
 */
public class Mailer {
    public static void send(String receiverHost, MailObject obj) {
        //provide recipient's email ID
        String to = receiverHost;
        //provide sender's email ID
        String from = "groovycineplex@gmail.com";
        //provide Google username
        final String username = "groovycineplex@gmail.com";
        //provide Google password
        final String password = "hnppeschbgtessig";
        //provide Mailtrap's host address
        String host = "smtp.gmail.com";
        //configure Mailtrap's SMTP server details
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        //create the Session object

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, authenticator);
        
        try {
            //create a MimeMessage object
            MimeMessage message = new MimeMessage(session);
            //set From email field
            message.setFrom(new InternetAddress(from));
            //set To email field
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            //set email subject field
            message.setSubject(obj.getSubject(), "UTF-8");
            //set the content of the email message
            message.setContent(obj.getContent(), "text/html; charset=UTF-8");
            //send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void sendMultiple(List<String> receiverHosts, MailObject obj) {
        //provide recipient's email ID
        List<String> to = receiverHosts;
        //provide sender's email ID
        String from = "groovycineplex@gmail.com";
        //provide Google username
        final String username = "groovycineplex@gmail.com";
        //provide Google password
        final String password = "hnppeschbgtessig";
        //provide Mailtrap's host address
        String host = "smtp.gmail.com";
        //configure Mailtrap's SMTP server details
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        //create the Session object

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, authenticator);
        
        try {
            //create a MimeMessage object
            MimeMessage message = new MimeMessage(session);
            //set From email field
            message.setFrom(new InternetAddress(from));
            //set To email field
            for (int i = 0; i < to.size(); i++) {
                if (i == 0) {
                    message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to.get(i)));
                } else {
                    message.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(to.get(i)));
                }
                
            }
            
            
            //set email subject field
            message.setSubject(obj.getSubject(), "UTF-8");
            //set the content of the email message
            message.setContent(obj.getContent(), "text/html; charset=UTF-8");
            //send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void blindSendMultiple(List<String> receiverHosts, MailObject obj) {
        //provide recipient's email ID
        List<String> to = receiverHosts;
        //provide sender's email ID
        String from = "groovycineplex@gmail.com";
        //provide Google username
        final String username = "groovycineplex@gmail.com";
        //provide Google password
        final String password = "hnppeschbgtessig";
        //provide Mailtrap's host address
        String host = "smtp.gmail.com";
        //configure Mailtrap's SMTP server details
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        //create the Session object

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, authenticator);
        
        try {
            //create a MimeMessage object
            MimeMessage message = new MimeMessage(session);
            //set From email field
            message.setFrom(new InternetAddress(from));
            //set To email field
            //set To email field
            for (int i = 0; i < to.size(); i++) {
                if (i == 0) {
                    message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to.get(i)));
                } else {
                    message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(to.get(i)));
                }
                
            }
            //set email subject field
            message.setSubject(obj.getSubject(), "UTF-8");
            
            for (int i = 0; i < to.size(); i++) {
                if (i == 0) {
                    message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to.get(i)));
                } else {
                    message.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(to.get(i)));
                }
                
            }
            //set the content of the email message
            message.setContent(obj.getContent(), "text/html; charset=UTF-8");
            //send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
