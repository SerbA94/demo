package com.demo.web.command.redirect;


import org.apache.log4j.Logger;

import com.demo.db.dao.UserDAO;
import com.demo.db.entity.Role;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;
import com.demo.web.utils.EncodeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;


public class LoginCommand extends Command implements Redirector {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger log = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		log.debug("Command starts");

		// error handler
		String errorMessage = null;
		String redirect = Path.COMMAND__VIEW_ERROR;

		// obtain login and password from the request
		String login = request.getParameter("login");
		log.trace("Request parameter: loging --> " + login);
		String password = EncodeUtil.hashSHA256(request.getParameter("password"));
		log.trace("Request parameter: password --> ********");



		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		log.trace("user from session --> " + user);


		/*
		 * if(user!=null) { if(!user.getLogin().equals(login)){ errorMessage =
		 * "You do not have permission to access this command.";
		 * request.setAttribute("errorMessage", errorMessage);
		 * log.error("errorMessage --> " + errorMessage); return forward; }else
		 * if(user.getRole().contains(Role.INACTIVE)) { forward = Path.PAGE__ACTIVATION;
		 * return forward; }else{ forward = Path.PAGE__WELCOME; return forward; } }
		 */

		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			errorMessage = "Login/password cannot be empty";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return redirect;
		}

		user = new UserDAO().findUserByLogin(login);
		log.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			errorMessage = "Cannot find user with such login/password";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return redirect;
		} else {
			Role userRole = (Role) user.getRole().toArray()[0];
			log.trace("userRole --> " + userRole);

			/*
			 * if (userRole == Role.ADMIN) forward = Path.COMMAND__LIST_ORDERS;
			 *
			 * if (userRole == Role.CUSTOMER) forward = Path.COMMAND__LIST_MENU;
			 */

			if (userRole == Role.INACTIVE) {
				redirect = Path.COMMAND__VIEW_ACTIVATION;
			}else{
				redirect = Path.COMMAND__VIEW_WELCOME;
			}

			session.setAttribute("user", user);
			log.trace("Set the session attribute: user --> " + user);

			session.setAttribute("userRole", userRole);
			log.trace("Set the session attribute: userRole --> " + userRole);

			log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

			// work with i18n
			String userLocaleName = user.getLocaleName();
			log.trace("userLocalName --> " + userLocaleName);

			if (userLocaleName != null && !userLocaleName.isEmpty()) {
				Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);

				session.setAttribute("defaultLocale", userLocaleName);
				log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);

				log.info("Locale for user: defaultLocale --> " + userLocaleName);
			}
		}

		log.debug("Command finished");

		return redirect;
	}
}