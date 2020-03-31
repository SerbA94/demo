/**
 *
 */
package com.demo.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.BookingRequestDAO;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.Role;
import com.demo.db.entity.User;
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
			errorMessage = "Booking request not exists id : id --> " + bookingRequestId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		User user = (User) request.getSession().getAttribute("user");
		log.trace("User from session --> " + user);

		if(user.getRole().contains(Role.CUSTOMER)) {
			if(bookingRequest.getUser().getId() != user.getId()) {
				errorMessage = "You don't have booking request id : id --> " + bookingRequestId;
				log.error("errorMessage --> " + errorMessage);
				request.setAttribute("errorMessage", errorMessage);
				return uri;
			}
			uri = Path.COMMAND__VIEW_ACCOUNT;
		}

		bookingRequestDAO.deleteBookingRequest(bookingRequest);

		log.debug("Command finished.");
		return uri;
	}

}
