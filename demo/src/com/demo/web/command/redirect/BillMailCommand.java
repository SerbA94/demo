package com.demo.web.command.redirect;

import java.io.IOException;

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
import com.demo.web.utils.MailUtil;

public class BillMailCommand extends Command implements Redirector {

	private static final long serialVersionUID = -5375942259978927403L;

	private static final Logger log = Logger.getLogger(BillMailCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String errorMessage = null;
		String redirect = Path.COMMAND__VIEW_ACCOUNT;

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
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		if (booking == null || !booking.getUser().getId().equals(user.getId())) {
			errorMessage = "You don't have booking with id : id --> " + bookingId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		if(booking.getBookingStatus().contains(BookingStatus.NOT_PAID)) {
			String subject = "Booking bill.";
			String messageText = "Bill for booking : " + booking + System.lineSeparator()
								 + "Total price : " + booking.getTotalPrice();
			new MailUtil().sendEmail(user.getEmail(), subject, messageText);

			// Add message
		}else {
			errorMessage = "Booking closed for payment.";
			request.setAttribute("errorMessage", errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
		}
		log.debug("Command finished.");
		return redirect;
	}
}
