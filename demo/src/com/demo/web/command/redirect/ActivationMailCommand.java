package com.demo.web.command.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.entity.Role;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;
import com.demo.web.utils.MailUtil;

public class ActivationMailCommand extends Command implements Redirector {

	private static final long serialVersionUID = -7628581063499337507L;

	private static final Logger log = Logger.getLogger(ActivationMailCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String errorMessage = null;
		String redirect = Path.COMMAND__VIEW_ACTIVATION;

		User user = (User)request.getSession().getAttribute("user");

		if(user.getRole().contains(Role.INACTIVE) && user.getActivationToken()!=null) {

			String subject = "Account activation";
			String messageText = "Click on the link to confirm account activation : " +
								 "http://localhost:8080/demo"+ Path.COMMAND__ACTIVATION +
								 "&email=" + user.getEmail() +
								 "&activation_token=" + user.getActivationToken();

			new MailUtil().sendEmail(user.getEmail(), subject, messageText);

		}else {
			errorMessage = "User already activated : email --> " + user.getEmail();
			request.setAttribute("errorMessage", errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			log.debug("Activation failed : " + errorMessage);
		}
		log.debug("Command finished.");
		return redirect;
	}

}
