/**
 *
 */
package com.demo.web.command;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.constants.Regex;
import com.demo.db.dao.BookingRequestDAO;
import com.demo.db.dao.RoomClassDAO;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.BookingRequestStatus;
import com.demo.db.entity.RoomClass;
import com.demo.db.entity.User;
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

		User user = (User) request.getSession().getAttribute("user");
		log.trace("User from session --> " + user);

		Integer capacity = null;
		try {
			capacity = Integer.parseInt(request.getParameter("capacity"));
		} catch (NumberFormatException e) {
			errorMessage = "Invalid capacity input format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return new BookingRequestCreateViewCommand().execute(request, response);
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
			return new BookingRequestCreateViewCommand().execute(request, response);
		}
		log.trace("Matched room class: roomClass --> " + roomClass);

		String dateInParam = request.getParameter("dateIn");
		log.trace("Request parameter: dateIn --> " + dateInParam);
		if(dateInParam == null || !dateInParam.matches(Regex.DATE_FORMAT)) {
			errorMessage = "Invalid dateIn input format : " + dateInParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new BookingRequestCreateViewCommand().execute(request, response);
		}
		Timestamp dateIn = TimestampUtil.parseTimestamp(dateInParam);

		String dateOutParam = request.getParameter("dateOut");
		log.trace("Request parameter: dateOut --> " + dateOutParam);
		if(dateOutParam == null || !dateOutParam.matches(Regex.DATE_FORMAT)) {
			errorMessage = "Invalid dateOut input format : " + dateOutParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new BookingRequestCreateViewCommand().execute(request, response);
		}
		Timestamp dateOut = TimestampUtil.parseTimestamp(dateOutParam);

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
			return new BookingRequestCreateViewCommand().execute(request, response);
		}

		Set<BookingRequestStatus> brs = Collections.singleton(BookingRequestStatus.ACTIVE);
		BookingRequest bookingRequest = new BookingRequest(user, capacity, dateIn, dateOut, roomClass, brs);
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
