/**
 *
 */
package com.demo.web.command.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.BookingRequestDAO;
import com.demo.db.entity.BookingRequest;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

/**
 * Booking request delete command.
 *
 * @author A.Serbin
 *
 */
public class BookingRequestDeleteCommand extends Command {

	private static final long serialVersionUID = -9036453675202149509L;
	private static final Logger log = Logger.getLogger(BookingRequestDeleteCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String uri = Path.COMMAND__VIEW_BOOKING_REQUEST_LIST;
		String errorMessage = null;

		Long bookingRequestId = null;
		try {
			bookingRequestId = Long.parseLong(request.getParameter("booking_request_id"));
			BookingRequest bookingRequest = new BookingRequest();
			bookingRequest.setId(bookingRequestId);
			new BookingRequestDAO().deleteBookingRequest(bookingRequest);
		} catch (NumberFormatException e) {
			errorMessage = "Invalid booking request id : id --> " + bookingRequestId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			uri = Path.PAGE__ERROR;
		}

		log.debug("Command finished.");
		return uri;
	}

}
