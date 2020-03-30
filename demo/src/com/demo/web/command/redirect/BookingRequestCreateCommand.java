/**
 *
 */
package com.demo.web.command.redirect;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.dao.BookingRequestDAO;
import com.demo.db.dao.RoomClassDAO;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.RoomClass;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;
import com.demo.web.utils.TimestampUtil;

/**
 * Booking request create command.
 *
 * @author A.Serbin
 *
 */
public class BookingRequestCreateCommand extends Command {

	private static final long serialVersionUID = 6937732969299796074L;
	private static final Logger log = Logger.getLogger(BookingRequestCreateCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String uri = Path.PAGE__ERROR;
		String errorMessage = null;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		log.trace("User from session --> " + user);

		Integer capacity = null;
		try {
			capacity = Integer.parseInt(request.getParameter("capacity"));
		} catch (NumberFormatException e) {
			errorMessage = "Invalid capacity input format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}
		log.trace("Request parameter: capacity --> " + capacity);

		String roomClassParam = request.getParameter("roomClass");
		log.trace("Request parameter: roomClass --> " + roomClassParam);

		Set<RoomClass> roomClass = roomClassParam == null ? null
				: RoomClassDAO.getRoomClassSet(roomClassParam);
		if(roomClass.contains(null)) {
			errorMessage = "Invalid roomClass input format : " + roomClassParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}
		log.trace("Matched room class: roomClass --> " + roomClass);

		String dateInParam = request.getParameter("dateIn");
		log.trace("Request parameter: dateIn --> " + dateInParam);
		Timestamp dateIn = dateInParam == null ? null : TimestampUtil.parseTimestamp(dateInParam);
		if(dateIn == null) {
			errorMessage = "Invalid dateIn input format : " + dateInParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		String dateOutParam = request.getParameter("dateOut");
		log.trace("Request parameter: dateOut --> " + dateOutParam);
		Timestamp dateOut = dateOutParam == null ? null : TimestampUtil.parseTimestamp(dateOutParam);
		if(dateOut == null) {
			errorMessage = "Invalid dateOut input format : " + dateOutParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		StringBuilder errorBuilder = new StringBuilder();
		if(dateIn.before(TimestampUtil.getNextDateIn())) {
			errorBuilder
				.append("Booking on : ").append(dateIn).append(" - closed.").append(System.lineSeparator())
				.append("The closest open booking date : ").append(TimestampUtil.getNextDateIn());
		}
		if(dateIn.after(dateOut) || dateIn.equals(dateOut)) {
			if(errorBuilder.length() != 0) {
				errorBuilder.append(System.lineSeparator());
			}
			errorBuilder.append("Date out can't be less than or equal date in.");
		}

		if(errorBuilder.length() != 0) {
			errorMessage = errorBuilder.toString();
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		BookingRequest bookingRequest = new BookingRequest(user, capacity, dateIn, dateOut, roomClass);
		bookingRequest = new BookingRequestDAO().createBookingRequest(bookingRequest);

		if(bookingRequest == null || bookingRequest.getId() == null) {
			errorMessage = "Booking request creation failed : Booking request was not created.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		uri = Path.COMMAND__VIEW_ACCOUNT;

		log.debug("Command finished.");
		return uri;
	}

}
