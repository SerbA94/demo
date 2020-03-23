package com.demo.web.command.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class BookingCreateViewCommand extends Command {

	private static final long serialVersionUID = -2582379197846722054L;
	private static final Logger log = Logger.getLogger(BookingCreateViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String forward = Path.PAGE__CUSTOMER_BOOKING_CREATE;

		log.debug("Command finished");
		return forward;
	}

}