/**
 *
 */
package com.demo.web.command;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.BookingRequestDAO;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.BookingRequestStatus;
import com.demo.web.constants.Path;

public class BookingRequestInactivateCommand extends Command {

	private static final long serialVersionUID = 5456617917864585963L;
	private static final Logger log = Logger.getLogger(BookingRequestInactivateCommand.class);


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
			bookingRequest.setBookingRequestStatus(Collections.singleton(BookingRequestStatus.INACTIVE));
			new BookingRequestDAO().updateBookingRequest(bookingRequest);
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
