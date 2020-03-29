package com.demo.web.command.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class ErrorViewCommand extends Command {

	private static final long serialVersionUID = -2582881304545931428L;
	private static final Logger log = Logger.getLogger(ErrorViewCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		log.debug("Command finished.");
		return Path.PAGE__ERROR;
	}

}
