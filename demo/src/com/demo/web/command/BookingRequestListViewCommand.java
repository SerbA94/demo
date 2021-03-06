/**
 *
 */
package com.demo.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.BookingRequestDAO;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.BookingRequestStatus;
import com.demo.web.constants.Path;

/**
 * Booking requests list view.
 *
 * @author A.Serbin
 *
 */
public class BookingRequestListViewCommand extends Command {

	private static final long serialVersionUID = -6416961521160878247L;
	private static final Logger log = Logger.getLogger(BookingRequestListViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		BookingRequestDAO bookingRequestDAO = new BookingRequestDAO();
		List<BookingRequest> bookingRequests = bookingRequestDAO
				.findBookingRequestsByStatus(BookingRequestStatus.ACTIVE);
		request.setAttribute("bookingRequests", bookingRequests);

		log.debug("Command finished.");
		return Path.PAGE__MANAGER_BOOKING_REQUEST_LIST;
	}

}
