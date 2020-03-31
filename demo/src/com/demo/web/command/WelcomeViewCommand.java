/**
 *
 */
package com.demo.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.web.constants.Path;

/**
 * Welcome view.
 *
 * @author A.Serbin
 *
 */
public class WelcomeViewCommand extends Command {

	private static final long serialVersionUID = 7800664786060821390L;
	private static final Logger log = Logger.getLogger(WelcomeViewCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		log.debug("Command finished.");
		return Path.PAGE__WELCOME;
	}

}
