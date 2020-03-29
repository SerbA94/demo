package com.demo.web.command.forward;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.dao.BookingDAO;
import com.demo.db.dao.BookingRequestDAO;
import com.demo.db.entity.Booking;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class AccountViewCommand extends Command {

	private static final long serialVersionUID = -5091799202096985039L;
	private static final Logger log = Logger.getLogger(AccountViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		log.trace("user from session --> " + user);

		BookingRequestDAO bookingRequestDAO =  new BookingRequestDAO();
		List<BookingRequest> bookingRequests = bookingRequestDAO.findBookingRequestsByUser(user);
		log.trace("bookingRequests size : size --> " + bookingRequests.size());
		request.setAttribute("bookingRequests", bookingRequests);

		BookingDAO bookingDAO = new BookingDAO();

		List<Booking> activeBookings = bookingDAO.findActiveBookingsByUser(user);
		log.trace("activeBookings size : size --> " + activeBookings.size());
		request.setAttribute("activeBookings", activeBookings);

		List<Booking> handlingBookings = bookingDAO.findHandlingBookingsByUser(user);
		log.trace("handlingBookings size : size --> " + handlingBookings.size());
		request.setAttribute("handlingBookings", handlingBookings);

		log.debug("Command finished.");
		return Path.PAGE__CUSTOMER_ACCOUNT;
	}

}
