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
import com.demo.web.utils.MailUtil;

public class BookingRequestInactivateCommand extends Command {

	private static final long serialVersionUID = 5456617917864585963L;
	private static final Logger log = Logger.getLogger(BookingRequestInactivateCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String uri = Path.PAGE__ERROR;
		String errorMessage = null;

		Long bookingRequestId = null;
		try {
			bookingRequestId = Long.parseLong(request.getParameter("booking_request_id"));
		} catch (NumberFormatException e) {
			errorMessage = "Invalid booking request id : id --> " + bookingRequestId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		BookingRequestDAO bookingRequestDAO = new BookingRequestDAO();
		BookingRequest bookingRequest = bookingRequestDAO.findBookingRequestById(bookingRequestId);
		if(bookingRequest == null) {
			errorMessage = "No such booking request.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		bookingRequest.setBookingRequestStatus(Collections.singleton(BookingRequestStatus.INACTIVE));
		bookingRequestDAO.updateBookingRequest(bookingRequest);

		String subject = "Booking reject by MANAGER.";
		String messageText = "Your booking request was rejected.";
		log.trace("Bill mail was sent to user : email --> " + bookingRequest.getUser().getEmail());
		new Thread(() -> new MailUtil()
				.sendEmail(bookingRequest.getUser().getEmail(), subject, messageText)).start();

		uri = Path.COMMAND__VIEW_BOOKING_REQUEST_LIST;

		log.debug("Command finished.");
		return uri;
	}

}
