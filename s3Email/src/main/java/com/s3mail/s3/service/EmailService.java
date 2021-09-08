package com.s3mail.s3.service;


import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
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
	        SecurityManager security = System.getSecurityManager();

	        try
	        {
	            Authenticator auth = new SMTPAuthenticator();
	            Session session = Session.getInstance(props, auth);
	            session.setDebug(true);
	            MimeMessage msg = new MimeMessage(session);
	            String html ="\n<a href="+url+">LINK</a>";
	            mail_subject = "PV DATA FILE";
	            mail_content = 
		    " Hello "+ name +"\n  Click on this "+ html+ " to download the file you requested !!\n"; 		
	            msg.setText(mail_content,"UTF-8", "html");
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