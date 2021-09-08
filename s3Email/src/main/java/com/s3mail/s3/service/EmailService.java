package com.s3mail.s3.service;


import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;


@Service
public class EmailService {

	
	String  from_address = "rahulpagidala999@gmail.com",
            password = "dellcharger@9",
            host = "smtp.gmail.com",
            port = "465",
            to_address = "pagidala9@gmail.com",
            mail_subject = "PV DUMP FILE LINK",
            mail_content = "Please click on the below donwload link to get the file\n";
			
	
	
	 public String sendmail(String name,String url)
	    {
		 
	        Properties props = new Properties();
	        props.put("mail.smtp.user", from_address);
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", port);
	        props.put("mail.smtp.starttls.enable","true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.debug", "true");
	        props.put("mail.smtp.socketFactory.port", port);
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.socketFactory.fallback", "false");
	        
	        mail_content = 
	        		 " Hello "+ name +"\nClick on the below link to download your requested file !!\n\n" + url;
	        		
	        mail_subject = "PV DUMP FILE LINK";
	        SecurityManager security = System.getSecurityManager();

	        try
	        {
	            Authenticator auth = new SMTPAuthenticator();
	            Session session = Session.getInstance(props, auth);
	            session.setDebug(true);
	            MimeMessage msg = new MimeMessage(session);
	            msg.setText(mail_content);
	            msg.setSubject(mail_subject);
	            msg.setFrom(new InternetAddress(from_address));
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_address));
	            Transport.send(msg);
	        }
	        catch (Exception mex)
	        {
	            mex.printStackTrace();
	        } 
	        
	        return "sent email";

	    }
	 
	 
	 private class SMTPAuthenticator extends javax.mail.Authenticator
	    {
	        @Override
			public PasswordAuthentication getPasswordAuthentication()
	        {
	            return new PasswordAuthentication(from_address, password);
	        }
	    }


}