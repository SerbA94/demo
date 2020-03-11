package com.demo.web.command.redirect;

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
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class ActivationCommand extends Command implements Redirector {

	private static final long serialVersionUID = 4828904762551760108L;

	private static final Logger log = Logger.getLogger(ActivationCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Command starts");

		UserDAO userDao = new UserDAO();

		HttpSession session = request.getSession();

		String errorMessage =  "Link invalid.";
		String redirect = Path.COMMAND__VIEW_ERROR;

		String email = request.getParameter("email");
		String activationToken = request.getParameter("activation_token");

		User user = userDao.findUserByEmail(email);

		if(user == null) {
			log.trace("Activation failed : user not exist. Email : " + email);
			request.setAttribute("errorMessage", errorMessage);
			return redirect;
		} else if(!user.getRole().contains(Role.INACTIVE) && user.getActivationToken() == null) {
			log.trace("Activation failed : user already activated. User : " + user);
			request.setAttribute("errorMessage", errorMessage);
			return redirect;
		} else if(user.getActivationToken() == activationToken) {
			log.trace("Activation failed : diffrent activation tokens ("
					+ activationToken + " <-- vs --> " + user.getActivationToken() + ")");
			request.setAttribute("errorMessage", errorMessage);
			return redirect;
		} else {
			user.setRole(Collections.singleton(Role.CUSTOMER));
			user.setActivationToken(null);
			userDao.updateUser(user);
			redirect = Path.COMMAND__VIEW_WELCOME;
			log.debug("User activated : " + user);

			session.setAttribute("user", user);
			log.trace("Set the session attribute: user --> " + user);

			session.setAttribute("userRole", Role.CUSTOMER);
			log.trace("Set the session attribute: userRole --> " + Role.CUSTOMER);

			log.info("User " + user + " logged as " + Role.CUSTOMER);

			log.debug("Command ends");
			return redirect;
		}

	}

}
