/**
 *
 */
package com.demo.web.command.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.web.command.Command;
import com.demo.web.command.redirect.ActivationCommand;
import com.demo.web.constants.Path;

/**
 * User account activation view.
 *
 * @author A.Serbin
 *
 */
public class ActivationViewCommand extends Command {

	private static final long serialVersionUID = 4438395985208271977L;

	private static final Logger log = Logger.getLogger(ActivationCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		log.debug("Command finished.");
		return Path.PAGE__INACTIVE_ACTIVATION;
	}

}
