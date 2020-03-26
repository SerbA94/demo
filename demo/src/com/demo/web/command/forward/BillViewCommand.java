package com.demo.web.command.forward;

import java.io.IOException;
import java.sql.SQLException;

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

public class BillViewCommand extends Command {

	private static final long serialVersionUID = -1622755176744008420L;
	private static final Logger log = Logger.getLogger(BillViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Command starts");

		String errorMessage = null;
		String forward = Path.PAGE__CUSTOMER_BILL;

		User user = (User) request.getSession().getAttribute("user");
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
			forward = Path.PAGE__ERROR;
			return forward;
		}

		if (booking == null || !booking.getUser().getId().equals(user.getId())) {
			errorMessage = "You dont have booking with id : id --> " + bookingId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			forward = Path.PAGE__ERROR;
			return forward;
		}

		if (booking.getBookingStatus().toArray()[0].equals(BookingStatus.NOT_PAID)) {
			String billDetails = "Bill for booking : " + booking + System.lineSeparator() + "Total price : "
					+ booking.getTotalPrice();
			request.setAttribute("billDetails", billDetails);
		} else {
			errorMessage = "No bill for booking : id --> " + booking.getId();
			request.setAttribute("errorMessage", errorMessage);
			forward = Path.PAGE__ERROR;
		}
		log.debug("Command ends");
		return forward;
	}

}
