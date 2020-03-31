/**
 *
 */
package com.demo.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.BookingDAO;
import com.demo.db.entity.Booking;
import com.demo.db.entity.BookingStatus;
import com.demo.db.entity.User;
import com.demo.web.constants.Path;

/**
 * Booking delete command.
 *
 * @author A.Serbin
 *
 */
public class BookingDeleteCommand extends Command {

	private static final long serialVersionUID = -9036453675202149509L;
	private static final Logger log = Logger.getLogger(BookingDeleteCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String errorMessage = null;
		String uri = Path.PAGE__ERROR;

		User user = (User)request.getSession().getAttribute("user");
		log.trace("user from session --> " + user);

		Long bookingId = null;
		Booking booking = null;
		try {
			bookingId = Long.parseLong(request.getParameter("booking_id"));
			booking = new BookingDAO().findBookingById(bookingId);
		} catch (NumberFormatException e) {
			errorMessage = "Invalid booking id : id --> " + bookingId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		if (booking == null || !booking.getUser().getId().equals(user.getId())) {
			errorMessage = "You dont have booking with id : id --> " + bookingId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		if (booking.getBookingStatus().contains(BookingStatus.UNCONFIRMED) ||
				booking.getBookingStatus().contains(BookingStatus.NOT_PAID)) {

			new BookingDAO().deleteBooking(booking);
			log.trace("Booking deleted : id --> " + booking.getId());
			String message = "Booking successfuly rejected.";
			request.setAttribute("message", message);

			uri = Path.COMMAND__VIEW_ACCOUNT;

			log.debug("Command finished.");
			return uri;
		}
		errorMessage = "Booking can't be deleted : id --> " + bookingId;
		log.error("errorMessage --> " + errorMessage);
		request.setAttribute("errorMessage", errorMessage);
		return uri;
	}

}
