package com.demo.web.command.redirect;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.BookingDAO;
import com.demo.db.entity.Booking;
import com.demo.db.entity.BookingStatus;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class BookingConfirmCommand extends Command implements Redirector {


	private static final long serialVersionUID = 7730640371187329342L;

	private static final Logger log = Logger.getLogger(BookingConfirmCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String errorMessage = null;
		String redirect = Path.COMMAND__VIEW_ACCOUNT;

		User user = (User)request.getSession().getAttribute("user");
		log.trace("user from session --> " + user);

		Long bookingId = null;
		Booking booking = null;
		try {
			bookingId = Long.parseLong(request.getParameter("booking_id"));
			booking = new BookingDAO().findBookingById(bookingId);
		} catch (NumberFormatException | SQLException e) {
			errorMessage = "Invalid booking id : id --> " + bookingId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		if (!booking.getUser().getId().equals(user.getId())) {
			errorMessage = "You dont have booking with id : id --> " + bookingId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		booking.setBookingStatus(Collections.singleton(BookingStatus.NOT_PAID));

		try {
			new BookingDAO().updateBooking(booking);
		} catch (SQLException e) {
			errorMessage = "Invalid booking.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}
		log.trace("Booking updated --> " + booking);
		log.debug("Command ends");
		return redirect;
	}

}
