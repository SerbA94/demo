/**
 *
 */
package com.demo.web.command;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.dao.UserDAO;
import com.demo.db.entity.Role;
import com.demo.db.entity.User;
import com.demo.web.constants.Path;

/**
 * Activation command.
 *
 * @author A.Serbin
 *
 */
public class ActivationCommand extends Command {

	private static final long serialVersionUID = 4828904762551760108L;
	private static final Logger log = Logger.getLogger(ActivationCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String uri = Path.PAGE__ERROR;
		String errorMessage =  "Link invalid.";

		String email = request.getParameter("email");
		log.trace("Request parameter: email --> " + email);

		String activationToken = request.getParameter("activation_token");
		log.trace("Request parameter: activation_token --> " + activationToken);

		if(email == null || activationToken == null) {
			log.trace("Activation failed : Request is null.");
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		UserDAO userDao = new UserDAO();
		User user = userDao.findUserByEmail(email);

		if(user == null) {
			log.trace("No user in db : email --> " + email);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}else {
			log.trace("User loaded from db : email --> " + email);
		}

		HttpSession session = request.getSession();
		if(!user.getRole().contains(Role.INACTIVE)) {
			log.trace("Activation failed : User already activated. User role : "
					+ user.getRole().toArray()[0]);

			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		if(user.getActivationToken() == null) {
			log.trace("Activation failed : User activation token is null.");
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		log.trace("=====>>>> User token: " + user.getActivationToken());
		log.trace("=====>>>> URL  token: " + activationToken);

		if(!user.getActivationToken().equals(activationToken)) {
			log.trace("Activation failed : Activation tokens did not matched.");
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		uri = Path.COMMAND__VIEW_WELCOME;

		user.setRole(Collections.singleton(Role.CUSTOMER));
		user.setActivationToken(null);
		userDao.updateUser(user);
		log.debug("User activated : id --> " + user.getId());

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
