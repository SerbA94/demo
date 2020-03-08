package com.demo.web.command.redirect;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.constants.Regex;
import com.demo.db.dao.UserDao;
import com.demo.db.entity.Role;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;
import com.demo.web.utils.EncodeUtil;

public class RegistrationCommand extends Command implements Redirector {

	private static final long serialVersionUID = -5994772745509837239L;
	private static final Logger log = Logger.getLogger(RegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String redirect = Path.COMMAND__VIEW_ERROR;
		String errorMessage = null;
		UserDao userDao = new UserDao();
		HttpSession session = request.getSession();

		String login = request.getParameter("login");
		log.trace("Request parameter: loging --> " + login);
		if(!login.matches(Regex.LOGIN)) {
			errorMessage = "Login must: "+System.lineSeparator()+
							"		- starts with a letter "+System.lineSeparator()+
							"		- ends with a letter or digit "+System.lineSeparator()+
							"		- contain 2 - 20 characters";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return redirect;
		}
		if(userDao.findUserByLogin(login)!=null) {
			errorMessage = "User exists";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return redirect;
		}

		String email = request.getParameter("email");
		log.trace("Request parameter: email --> " + email);
		if(!email.matches(Regex.EMAIL)) {
			errorMessage = "Invalid email syntaxis";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return redirect;
		}
		if(userDao.findUserByEmail(email)!=null) {
			errorMessage = "Email exists";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return redirect;
		}

		String localeName = request.getParameter("locale-name");
		log.trace("Request parameter: localeName --> " + localeName);

		String password = request.getParameter("password");
		log.trace("Request parameter: password --> " + password);

		String passwordCheck = request.getParameter("password-check");
		log.trace("Request parameter: passwordCheck --> " + passwordCheck);

		if(!password.equals(passwordCheck)) {
			errorMessage = "Passwords not matched";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return redirect;
		}
		if(!password.matches(Regex.PASSWORD)){
			errorMessage = "Password should contain at least:"+System.lineSeparator()+
							"		- 1 lowercase alphabetical character "+System.lineSeparator()+
							"		- 1 uppercase alphabetical character "+System.lineSeparator()+
							"		- 1 numeric character "+System.lineSeparator()+
							"		- password must contain 8 - 20 characters";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return redirect;
		}

		User user = new User(login,EncodeUtil.hashSHA256(password),localeName,email,
							 Collections.singleton(Role.INACTIVE),
							 EncodeUtil.hashSHA256(email+login));
		userDao.createUser(user);

		log.trace("User registered:--> " + user.toString());

		session.setAttribute("user", user);
		log.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", Role.INACTIVE);
		log.trace("Set the session attribute: userRole --> " + Role.INACTIVE);

		log.info("User " + user + " logged as " + Role.INACTIVE);

		redirect = Path.COMMAND__ACTIVATION_MAIL;
		return redirect;
	}

}
