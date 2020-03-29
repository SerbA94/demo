package com.demo.web.command.redirect;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.constants.Regex;
import com.demo.db.dao.UserDAO;
import com.demo.db.entity.Role;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;
import com.demo.web.utils.EncodeUtil;

public class RegistrationCommand extends Command {

	private static final long serialVersionUID = -5994772745509837239L;
	private static final Logger log = Logger.getLogger(RegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String uri = Path.PAGE__REGISTRATION;
		String errorMessage = null;
		UserDAO userDao = new UserDAO();

		String login = request.getParameter("login");
		log.trace("Request parameter: loging --> " + login);
		if(login == null || login.isEmpty()) {
			errorMessage = "Login can't be empty or null.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}
		if(!login.matches(Regex.LOGIN)) {
			errorMessage = "Login must: " + System.lineSeparator() +
							"		- starts with a letter " + System.lineSeparator() +
							"		- ends with a letter or digit " + System.lineSeparator() +
							"		- contain 2 - 20 characters";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		String email = request.getParameter("email");
		log.trace("Request parameter: email --> " + email);
		if(email == null || email.isEmpty()) {
			errorMessage = "Email can't be empty or null.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}
		if(!email.matches(Regex.EMAIL)) {
			errorMessage = "Invalid email syntaxis.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		if(userDao.findUserByLogin(login) != null) {
			errorMessage = "User with login exists : login --> " + login;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		if(userDao.findUserByEmail(email)!=null) {
			errorMessage = "User with email exists : email --> " + email;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		String localeName = request.getParameter("locale-name");
		log.trace("Request parameter: localeName --> " + localeName);

		String password = request.getParameter("password");
		log.trace("Request parameter: password --> " + password);
		if(password == null || password.isEmpty()) {
			errorMessage = "Password can't be empty or null.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		String passwordCheck = request.getParameter("password-check");
		log.trace("Request parameter: passwordCheck --> " + passwordCheck);
		if(passwordCheck == null || passwordCheck.isEmpty()) {
			errorMessage = "Password can't be empty or null.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		if(!password.equals(passwordCheck)) {
			errorMessage = "Passwords don't match.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		if(!password.matches(Regex.PASSWORD)){
			errorMessage = "Password should contain at least : " + System.lineSeparator() +
							"		- 1 lowercase alphabetical character " + System.lineSeparator() +
							"		- 1 uppercase alphabetical character " + System.lineSeparator() +
							"		- 1 numeric character " + System.lineSeparator() +
							"		- password must contain 8 - 20 characters";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		User user = new User(login,EncodeUtil.hashSHA256(password),localeName,email,
							 Collections.singleton(Role.INACTIVE),
							 EncodeUtil.hashSHA256(email+login));

		user = userDao.createUser(user);

		if(user == null) {
			errorMessage = "Registration failed, user was not created.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}
		log.trace("User registered : id --> " + user.getId());

		uri = Path.COMMAND__ACTIVATION_MAIL;

		HttpSession session = request.getSession();

		session.setAttribute("user", user);
		log.trace("Set the session attribute: user --> " + user);

		Role userRole = (Role) user.getRole().toArray()[0];
		session.setAttribute("userRole", userRole);
		log.trace("Set the session attribute: userRole --> " + userRole);

		log.info("User " + user + " logged as " + userRole.getTitle());

		log.debug("Command finished.");
		return uri;
	}
}
