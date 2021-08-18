package com.qa.Utilities;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
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

import org.openqa.selenium.WebDriver;

import com.qa.MainFunctions.DriverCalling;
import com.qa.MainFunctions.GlobalConstant;

import net.bytebuddy.agent.builder.AgentBuilder.CircularityLock.Global;

public class SendEmailwithAttachment extends DriverCalling {

	static WebDriver driver = null;

	public SendEmailwithAttachment(WebDriver driver) {
		SendEmailwithAttachment.driver = driver;
	}
	
	public static void sendEmailReport(String comment) {
		String recipient ="priyanka@salesken.ai,raj@salesken.ai";//change accordingly  
		  final String username="priyanka@salesken.ai";//change accordingly  
		  final String password="S0ftw3aR@2021";//change accordingly  
		   
		    
		  String host = "smtp.gmail.com";// "relay.jangosmtp.net";//"smtp.office365.com"

			Properties props = null;
			if (props == null) {
				props = new Properties();
				props.put("mail.smtp.auth", true);
				props.put("mail.smtp.starttls.enable", true);
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.port", "25");
				props.put("mail.smtp.socketFactory.port", "587");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "true");
				props.put("mail.smtp.user", username);
				props.put("mail.smtp.pwd", password);
			}
		  
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(username,password);  
		   }  
		  });  
		     
		  //2) compose message     
		  try{  
		    MimeMessage message = new MimeMessage(session);  
		    message.setFrom(new InternetAddress(username));  
		    //message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		    //String recipient = "yamabs@gmail.com ,priya@gmail.com ";
		    String[] recipientList = recipient.split(",");
		    InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
		    int counter = 0;
		    for (String recipient1 : recipientList) {
		        recipientAddress[counter] = new InternetAddress(recipient1.trim());
		        counter++;
		    }
		    File f = new File(GlobalConstant.ExtentReport_Path);
		    String name = f.getName();
		    name = name.split("\\.")[0];
		    name = name.split("_")[1];
		    message.setRecipients(Message.RecipientType.TO, recipientAddress);
		    message.setSubject("Automation Execution Report - "+name+" "+comment);  
		      
		    //3) create MimeBodyPart object and set your message text     
		    BodyPart messageBodyPart1 = new MimeBodyPart();  
		    messageBodyPart1.setText("This is the execution report for the automation execution done at "+name);  
		      
		    //4) create new MimeBodyPart object and set DataHandler object to this object      
		    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		  
		    String filename = GlobalConstant.TestReport;//change accordingly  
		    DataSource source = new FileDataSource(filename);  
		    messageBodyPart2.setDataHandler(new DataHandler(source));  
		    messageBodyPart2.setFileName(filename);  
		     
		     
		    //5) create Multipart object and add MimeBodyPart objects to this object      
		    Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart1);  
		    multipart.addBodyPart(messageBodyPart2);  
		  
		    //6) set the multiplart object to the message object  
		    message.setContent(multipart );  
		     
		    //7) send message  
		    Transport.send(message);  
		   
		   System.out.println("Message sent successfully...");  
		   }catch (MessagingException ex) {ex.printStackTrace();}  
	}

	public static void sendEmail(String TC_ID, String dest, String datafilename) {
		String to = dest;

		// Sender's email ID needs to be mentioned
		String from = ExcelHandling.GetExcelData(TC_ID, "Sender");

		final String username = ExcelHandling.GetExcelData(TC_ID, "Sender");// change accordingly
		final String password = ExcelHandling.GetExcelData(TC_ID, "SenderPwd");// change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";// "relay.jangosmtp.net";//"smtp.office365.com"

		Properties props = null;
		if (props == null) {
			props = new Properties();
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.socketFactory.port", "587");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "true");
			props.put("mail.smtp.user", username);
			props.put("mail.smtp.pwd", password);
		}

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		// session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Sending mail from Automation");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText(ExcelHandling.GetExcelData(TC_ID, "Text"));

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = datafilename;
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
			throw new RuntimeException(e);
		}
	}

	
}
