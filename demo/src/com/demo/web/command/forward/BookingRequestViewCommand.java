package com.demo.web.command.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class BookingRequestViewCommand extends Command {

	private static final long serialVersionUID = -7670311821858038907L;
	private static final Logger log = Logger.getLogger(BookingRequestViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String forward = Path.PAGE__MANAGER_BOOKING_REQUEST;

		log.debug("Command finished");
		return forward;
	}

}
