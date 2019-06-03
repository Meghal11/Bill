package com.bb.billingsystem.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
	public static boolean sendMail() {

		 final String username = "bbhteam1011@gmail.com";
	     final String password = "msak1011";
	     AdminConstants ad = new AdminConstants();
	       String userid= ad.getUserid();
	       String email = ad.getEmail();
	       String to=email;
	       System.out.println(userid);
        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

        	MimeMessage message = new MimeMessage(session); 
            message.setFrom(new InternetAddress(username));  
            message.addRecipient(Message.RecipientType.TO,   
            new InternetAddress(to));  
            message.setSubject("Bulk Uploaded Successfully");  
            message.setText("Hey "+ Character.toString(userid.charAt(0)).toUpperCase()+userid.substring(1) + ",\r\n" + 
            		"\r\n" + 
            		"Your XLS uploaded successfully \r\n" + 
            		"\r\n" + 
            		"Best Regards\r\n" + 
            		"BBProjects Team");  

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
	
	public static boolean registerMail(String email) {

		 final String username = "bbhteam1011@gmail.com";
	     final String password = "msak1011";
	       String to = email;
	       AdminConstants ad = new AdminConstants();
	       String userid= ad.getUserid();
	       String pwd = ad.getPassword();
       Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
       prop.put("mail.smtp.port", "587");
       prop.put("mail.smtp.auth", "true");
       prop.put("mail.smtp.starttls.enable", "true"); //TLS
       
       Session session = Session.getInstance(prop,
               new javax.mail.Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
               });

       try {

           MimeMessage message = new MimeMessage(session); 
           message.setFrom(new InternetAddress(username));  
           message.addRecipient(Message.RecipientType.TO,   
           new InternetAddress(to));  
           message.setSubject("Welcome to BBProjects");  
           message.setText("Hey "+ Character.toString(userid.charAt(0)).toUpperCase()+userid.substring(1) + ",\r\n" + 
           		"\r\n" + 
           		"You have registered successfully with us. \r\n" +
           		"\r\n" + 
           		"Login ID: "+ userid+"\r\n"+"Password: "+ pwd +"\r\n"+"\r\n"+
           		"Best Regards\r\n" + 
           		"BBProjects Team");  

           Transport.send(message);

           System.out.println("Done");

       } catch (MessagingException e) {
           e.printStackTrace();
           return false;
       }
       return true;
   }
	
	public static boolean confirmMail(double total) {
		 final String username = "bbhteam1011@gmail.com";
	     final String password = "msak1011";
	     AdminConstants ad = new AdminConstants();
	       String userid= ad.getUserid();
	       String email = ad.getEmail();
	       String to=email;
	       System.out.println(userid);
       Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
       prop.put("mail.smtp.port", "587");
       prop.put("mail.smtp.auth", "true");
       prop.put("mail.smtp.starttls.enable", "true"); //TLS
       
       Session session = Session.getInstance(prop,
               new javax.mail.Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
               });

       try {

           MimeMessage message = new MimeMessage(session); 
           message.setFrom(new InternetAddress(username));  
           message.addRecipient(Message.RecipientType.TO,   
           new InternetAddress(to));  
           message.setSubject("Order Booked Successfully.");  
           message.setText("Hey "+ Character.toString(userid.charAt(0)).toUpperCase()+userid.substring(1) + ",\r\n" + 
           		"\r\n" + 
           		"Your order has been placed successfully \r\n" + 
           		"Order ID: BB@" + total + "MSAK1011  \r\n" + 
           		"\r\n" + 
           		"Best Regards\r\n" + 
           		"BBProjects Team");  

           Transport.send(message);

           System.out.println("Done");

       } catch (MessagingException e) {
           e.printStackTrace();
           return false;
       }
       return true;
   }
}
