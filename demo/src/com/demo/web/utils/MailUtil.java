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

import org.apache.log4j.Logger;

public class MailUtil {

	private static final Logger log = Logger.getLogger(MailUtil.class);

	public void sendEmail(String address, String subject, String messageText) {
		log.debug("Sending email starts.");

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

			log.debug("Sending email successfully finished.");
		} catch (MessagingException e) {
			e.printStackTrace();
			log.debug("Sending email interrupted: MessagingException");
		}
	}

	private Properties getProperties(){
		log.debug("Uploading properties starts.");
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mail.properties");
		if(inputStream!=null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				log.debug("Uploading properties interrupted : IOException");
				e.printStackTrace();
			}
		}
		log.debug("Uploading properties finished.");
		return prop;
	}

}
