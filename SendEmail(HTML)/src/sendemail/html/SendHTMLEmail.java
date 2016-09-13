/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemail.html;

import java.util.Properties;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendHTMLEmail {
   public static void main(String[] args) {
      // Recipient's email ID needs to be mentioned.
      String to = "david.anugrah@azec.co.id";

      // Sender's email ID needs to be mentioned
      String from = "davidanugrah1@gmail.com";
      final String username = "davidanugrah1@gmail.com";//change accordingly
      final String password = "manuakcc21";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "smtp.gmail.com";

      Properties props = new Properties();
//      props.put("mail.smtp.auth", "true");
//      props.put("mail.smtp.starttls.enable", "true");
//      props.put("mail.smtp.host", host);
//      props.put("mail.smtp.port", "25");

      props.put("mail.smtp.host", host);  
      props.put("mail.smtp.socketFactory.port", "465");  
      props.put("mail.smtp.socketFactory.class",  
                "javax.net.ssl.SSLSocketFactory");  
      props.put("mail.smtp.auth", "true");  
      props.put("mail.smtp.port", "465"); 

      
      // Get the Session object.
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication("davidanugrah1@gmail.com", "manuakcc21");
            }
	});

      try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

   	   // Set From: header field of the header.
	   message.setFrom(new InternetAddress(from));

	   // Set To: header field of the header.
	   message.setRecipients(Message.RecipientType.TO,
              InternetAddress.parse(to));
           
//            String body="Dear Renish Khunt Welcome";
//            String htmlBody = "<strong>This is an HTML Message</strong>";
//            String textBody = "Dear Renish Khunt Welcome : <b>This is a Text Message.";
//            
//            MailcapCommandMap mc = (MailcapCommandMap)  CommandMap.getDefaultCommandMap();
//            mc.addMailcap("text/html;;  x-java-content-handler=com.sun.mail.handlers.text_html");
//            mc.addMailcap("text/xml;;  x-java-content-handler=com.sun.mail.handlers.text_xml");
//            mc.addMailcap("text/plain;;  x-java-content-handler=com.sun.mail.handlers.text_plain");
//            mc.addMailcap("multipart/*;;  x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
//            mc.addMailcap("message/rfc822;;  x-java-content-handler=com.sun.mail.handlers.message_rfc822");
//
//            CommandMap.setDefaultCommandMap(mc);
//            message.setText(htmlBody);
//            message.setContent(textBody, "text/html");
            
//	    Set Subject: header field
	   message.setSubject("Testing Subject");
//
            BodyPart messageBodyPart = new MimeBodyPart();
//           
//	   // Send the actual HTML message, as big as you like
	   messageBodyPart.setContent(
              "<h1>Ini dari HTML</h1>"
                      + "<b>This is a Text Message",
             "text/html");
//           
          Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);
//
//         // Part two is attachment
         messageBodyPart = new MimeBodyPart();
         String filename = "E://fileExport22022016163345.txt";
         DataSource source = new FileDataSource(filename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filename);
         multipart.addBodyPart(messageBodyPart);

         // Send the complete message parts
         message.setContent(multipart);
           

	   // Send message
	   Transport.send(message);

	   System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
	   e.printStackTrace();
	   throw new RuntimeException(e);
      }
   }
}
