/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemailtls;

import java.io.*;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author azec
 */
public class SendEmailTLS {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.net.MalformedURLException
     * @throws java.security.KeyManagementException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws IOException, MalformedURLException, KeyManagementException, NoSuchAlgorithmException {
        final String username = "@azec.co.id";
        final String password = "";

        Properties props = new Properties();
        String mailserver = "mail.azec.co.id";
        String mailport = "465";
        String recipient = "@azec.co.id";
        String subject = "JavaMail";

        props.put("mail.smtp.host", mailserver);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", mailport);
        props.put("mail.smtp.ssl.enable", "true");

        props.put("mail.smtp.socketFactory.port", mailport);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.socketFactory.class", "sendemailtls.DummySSLSocketFactory");
        props.put("mail.smtp.ssl.socketFactory.fallback", "false");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
//            String body="Dear Renish Khunt Welcome";
//            String htmlBody = "<strong>This is an HTML Message</strong>";

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);

            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;;  x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;;  x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;;  x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;;  x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;;  x-java-content-handler=com.sun.mail.handlers.message_rfc822");

            CommandMap.setDefaultCommandMap(mc);
            BodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();
            messageBodyPart.setContent("<h1> Berhasil bro!</h1>"
                    + "<br>"
                    + "<b>javamail ini yang TLS, kita mesti bikin DummySSLSocket buat certificate nya biar di trust semua</b>"
                    + "<p>versi javamail terbaru 1.4.5<p>", "text/html");
            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "E://test.zip";

            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setSubject("Testing Subject");
            message.setContent(multipart);
//            message.setText(htmlBody);

//            message.setContent(messageBodyPart,"text/html");
            message.saveChanges();
            final StringBuilder sb = new StringBuilder();
            sb.append("Test String");

            final File f = new File("E:\\test.zip");
            final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
            ZipEntry e = new ZipEntry("fileExport22022016163345.txt");
            out.putNextEntry(e);

            byte[] data = sb.toString().getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();

            out.close();

            Transport.send(message);

            String messageout = "Mail sent";
            int err = 0;
            System.out.println("Mail sent");

        } catch (MessagingException e) {
            String messageout = Arrays.toString(e.getStackTrace());
            int err = 1;
        }

    }

//    public class UnzipUtilityTest {
////    public static void main(String[] args) {
//        String zipFilePath = "e:/Test/MyPics.zip";
//        String destDirectory = "f:/Pics";
//        UnzipUtility unzipper = new UnzipUtility();
//        try {
//            unzipper.unzip(zipFilePath, destDirectory);
//        } catch (Exception ex) {
//            // some errors occurred
//            ex.printStackTrace();
//        }
//    }
//   
}
