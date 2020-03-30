/**
 *
 */
package com.demo.web.command.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.web.command.Command;
import com.demo.web.constants.Path;

/**
 * Logout command.
 *
 * @author A.Serbin
 *
 */
public class LogoutCommand extends Command {

	private static final long serialVersionUID = -2785976616686657267L;
	private static final Logger log = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		log.debug("Command started.");

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		log.debug("Command finished.");
		return Path.COMMAND__VIEW_LOGIN;
	}

}