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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.constants.Regex;
import com.demo.db.dao.BookingDAO;
import com.demo.db.dao.BookingRequestDAO;
import com.demo.db.dao.RoomDAO;
import com.demo.db.dao.UserDAO;
import com.demo.db.entity.Booking;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.BookingStatus;
import com.demo.db.entity.Role;
import com.demo.db.entity.Room;
import com.demo.db.entity.RoomStatus;
import com.demo.db.entity.User;
import com.demo.web.constants.Path;
import com.demo.web.utils.MailUtil;
import com.demo.web.utils.TimestampUtil;

/**
 * Booking create command.
 *
 * @author A.Serbin
 *
 */
public class BookingCreateCommand extends Command {

	private static final long serialVersionUID = -3958465045591852433L;
	private static final Logger log = Logger.getLogger(BookingCreateCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String uri = Path.PAGE__ERROR;
		String errorMessage = null;

		Set<BookingStatus> bookingStatus = null;
		User user = null;

		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("user");
		log.trace("User from session --> " + loggedUser);

		Timestamp dateIn = null;
		Timestamp dateOut = null;
		Timestamp dateOfBooking = new Timestamp(System.currentTimeMillis());
		if(loggedUser.getRole().contains(Role.MANAGER)) {
			bookingStatus = Collections.singleton(BookingStatus.UNCONFIRMED);
			Long userId = null;
			try {
				userId = Long.parseLong(request.getParameter("user_id"));
				user = new UserDAO().findUserById(userId);
			} catch (NumberFormatException e) {
				log.trace("User id from request --> " + userId);
			}

			if(user == null) {
				errorMessage = "No user with id : id --> " + userId;
				log.error("errorMessage --> " + errorMessage);
				request.setAttribute("errorMessage", errorMessage);
				return uri;
			}

			Long bookingRequestId = null;
			BookingRequest bookingRequest = null;
			try {
				bookingRequestId = Long.parseLong(request.getParameter("booking_request_id"));

				BookingRequestDAO bookingRequestDAO = new BookingRequestDAO();
				bookingRequest = bookingRequestDAO.findBookingRequestById(bookingRequestId);
				bookingRequestDAO.deleteBookingRequest(bookingRequest);
			} catch (NumberFormatException e) {
				errorMessage = "Invalid booking request id : id --> " + bookingRequestId;
				log.error("errorMessage --> " + errorMessage);
				request.setAttribute("errorMessage", errorMessage);
				return uri;
			}

			dateIn = bookingRequest.getDateIn();
			dateOut = bookingRequest.getDateOut();

		}else {
			bookingStatus = Collections.singleton(BookingStatus.NOT_PAID);
			user = loggedUser;

			String dateInParam = request.getParameter("dateIn");
			log.trace("Request parameter: dateIn --> " + dateInParam);
			if(dateInParam == null || !dateInParam.matches(Regex.DATE_FORMAT)) {
				errorMessage = "Invalid dateIn input format : " + dateInParam;
				request.setAttribute("errorMessage", errorMessage);
				log.error("errorMessage --> " + errorMessage);
				return new BookingRequestCreateViewCommand().execute(request, response);
			}
			dateIn = TimestampUtil.parseTimestamp(dateInParam);

			String dateOutParam = request.getParameter("dateOut");
			log.trace("Request parameter: dateOut --> " + dateOutParam);
			if(dateOutParam == null || !dateOutParam.matches(Regex.DATE_FORMAT)) {
				errorMessage = "Invalid dateOut input format : " + dateOutParam;
				request.setAttribute("errorMessage", errorMessage);
				log.error("errorMessage --> " + errorMessage);
				return new BookingRequestCreateViewCommand().execute(request, response);
			}
			dateOut = TimestampUtil.parseTimestamp(dateOutParam);

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
		}

		Long roomId = null;
		try {
			roomId = Long.parseLong(request.getParameter("room_id"));
		} catch (NumberFormatException e) {
			errorMessage = "Invalid id format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}
		log.trace("Room id from request --> " + roomId);

		RoomDAO roomDAO = new RoomDAO();
		Room room = roomDAO.findRoomById(roomId);
		if(room == null) {
			errorMessage = "No room with id : id --> " + roomId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}
		log.trace("Room from db : id --> " + room.getId());

		if(!room.getRoomStatus().contains(RoomStatus.FREE)) {
			errorMessage = "Room cant be booked : room number --> " + room.getNumber();
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		BookingDAO bookingDAO = new BookingDAO();
		Booking booking = bookingDAO.createBooking(
				new Booking(user, room, bookingStatus, dateIn, dateOut, dateOfBooking));

		if(booking == null || booking.getId() == null) {
			errorMessage = "Booking creation failed : Booking was not created.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}
		log.trace("Booking successfuly created : id --> " + booking.getId());

		if(loggedUser.getRole().contains(Role.MANAGER)) {
			String subject = "Booking bill by MANAGER.";
			String messageText = "Booking created : " + booking + System.lineSeparator()
								 + "Total price : " + booking.getTotalPrice();
			log.trace("Booking confirmation sent to user : email --> " + booking.getUser().getEmail());
			new Thread(() -> new MailUtil()
					.sendEmail(booking.getUser().getEmail(), subject, messageText)).start();
			uri = Path.COMMAND__VIEW_BOOKING_REQUEST_LIST;
		}else {
			String subject = "Booking bill.";
			String messageText = "Bill for booking : " + booking + System.lineSeparator()
								 + "Total price : " + booking.getTotalPrice();
			new Thread(() -> new MailUtil()
					.sendEmail(booking.getUser().getEmail(), subject, messageText)).start();
			log.trace("Bill mail was sent to user : email --> " + booking.getUser().getEmail());
			uri = Path.COMMAND__VIEW_ACCOUNT;
		}

		log.debug("Command finished.");
		return uri;
	}
}
