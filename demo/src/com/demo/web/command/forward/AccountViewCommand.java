package com.demo.web.command.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class AccountViewCommand extends Command {

	private static final long serialVersionUID = -5091799202096985039L;
	private static final Logger log = Logger.getLogger(AccountViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String forward = Path.PAGE__CUSTOMER_ACCOUNT;


		log.debug("Command finished");
		return forward;
	}

}
