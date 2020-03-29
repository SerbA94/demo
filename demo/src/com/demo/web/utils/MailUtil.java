package com.demo.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

	public void sendEmail(String address, String subject, String messageText) {

		Properties prop = getProperties();

		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(prop.getProperty("mail.smtp.username"),
						prop.getProperty("mail.smtp.password"));
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(prop.getProperty("mail.smtp.username")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
			message.setSubject(subject);
			message.setText(messageText);

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private Properties getProperties(){
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mail.properties");
		if(inputStream!=null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

}
