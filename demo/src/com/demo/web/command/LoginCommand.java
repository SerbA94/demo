/**
 *
 */
package com.demo.web.command;

import org.apache.log4j.Logger;

import com.demo.db.dao.UserDAO;
import com.demo.db.entity.Role;
import com.demo.db.entity.User;
import com.demo.web.constants.Path;
import com.demo.web.utils.EncodeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * Login command.
 *
 * @author A.Serbin
 *
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;
	private static final Logger log = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		log.debug("Command started.");

		String errorMessage = null;
		String uri = Path.PAGE__LOGIN;

		String login = request.getParameter("login");
		log.trace("Request parameter: loging --> " + login);
		if(login == null || login.isEmpty()) {
			errorMessage = "Login can't be empty.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new LoginViewCommand().execute(request, response);
		}

		String password = request.getParameter("password");
		if(password == null || password.isEmpty()) {
			errorMessage = "Password can't be empty.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new LoginViewCommand().execute(request, response);
		}
		password = EncodeUtil.hashSHA256(password);
		log.trace("Request parameter: password --> ********");

		User user = new UserDAO().findUserByLogin(login);
		log.trace("User from db : user --> " + user);

		if (user == null) {
			errorMessage = "No such user : login --> " + login;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new LoginViewCommand().execute(request, response);
		}

		if (!password.equals(user.getPassword())) {
			errorMessage = "Password wrong.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new LoginViewCommand().execute(request, response);
		}

		Role userRole = (Role) user.getRole().toArray()[0];
		log.trace("userRole --> " + userRole);

		if (userRole == Role.INACTIVE) {
			uri = Path.COMMAND__VIEW_ACTIVATION;
		}else{
			uri = Path.COMMAND__VIEW_WELCOME;
		}

		HttpSession session = request.getSession();

		session.setAttribute("user", user);
		log.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		log.trace("Set the session attribute: userRole --> " + userRole);
		log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		String userLocaleName = user.getLocaleName();
		log.trace("userLocalName --> " + userLocaleName);
		if (userLocaleName != null && !userLocaleName.isEmpty()) {
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);

			session.setAttribute("defaultLocale", userLocaleName);
			log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);

			log.info("Locale for user: defaultLocale --> " + userLocaleName);
		}

		log.debug("Command finished.");
		return uri;
	}
}