package com.demo.web.command.redirect;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

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
import com.demo.web.command.Command;
import com.demo.web.constants.Path;
import com.demo.web.utils.TimestampUtil;

public class BookingCreateCommand extends Command implements Redirector {

	private static final long serialVersionUID = -3958465045591852433L;

	private static final Logger log = Logger.getLogger(BookingCreateCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String redirect = Path.COMMAND__VIEW_ACCOUNT;
		String errorMessage = null;

		Set<BookingStatus> bookingStatus = null;
		User user = null;

		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("user");
		log.trace("user from session --> " + loggedUser);
		Role userRole = (Role) loggedUser.getRole().toArray()[0];

		if(userRole.equals(Role.MANAGER)) {

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
				redirect = Path.COMMAND__VIEW_ERROR;
				return redirect;
			}

			Long bookingRequestId = null;
			try {
				bookingRequestId = Long.parseLong(request.getParameter("booking_request_id"));
				BookingRequest bookingRequest = new BookingRequest();
				bookingRequest.setId(bookingRequestId);
				new BookingRequestDAO().deleteBookingRequest(bookingRequest);
			} catch (NumberFormatException | SQLException e) {
				errorMessage = "Invalid booking request id : id --> " + bookingRequestId;
				log.error("errorMessage --> " + errorMessage);
				request.setAttribute("errorMessage", errorMessage);
				redirect = Path.COMMAND__VIEW_ERROR;
				return redirect;
			}
			redirect = Path.COMMAND__VIEW_BOOKING_REQUEST_LIST;
		}else {
			bookingStatus = Collections.singleton(BookingStatus.NOT_PAID);
			user = loggedUser;
		}

		Long roomId = null;
		try {
			roomId = Long.parseLong(request.getParameter("room_id"));
		} catch (NumberFormatException e) {
			errorMessage = "Invalid id format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}
		log.trace("Room id from request --> " + roomId);

		RoomDAO roomDAO = new RoomDAO();
		Room room = roomDAO.findRoomById(roomId);
		if(room == null) {
			errorMessage = "No room with id : id --> " + roomId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}
		log.trace("Room from db  --> " + room);

		RoomStatus roomStatus = (RoomStatus) room.getRoomStatus().toArray()[0];
		if(!roomStatus.equals(RoomStatus.FREE)) {
			errorMessage = "Room cant be booked : room number --> " + room.getNumber();
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		String dateInStr = request.getParameter("dateIn");
		log.trace("Request parameter: dateIn --> " + dateInStr);
		Timestamp dateIn = TimestampUtil.parseTimestamp(dateInStr);
		if(dateInStr == null || dateInStr.isEmpty() || dateIn == null) {
			errorMessage = "Invalid dateIn input format : " + dateInStr;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		String dateOutStr = request.getParameter("dateOut");
		log.trace("Request parameter: dateOut --> " + dateOutStr);
		Timestamp dateOut = TimestampUtil.parseTimestamp(dateOutStr);
		if(dateOutStr == null || dateOutStr.isEmpty() || dateOut == null) {
			errorMessage = "Invalid dateOut input format : " + dateOutStr;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		Timestamp dateOfBooking = new Timestamp(System.currentTimeMillis());
		if(dateIn.after(dateOut) || dateIn.equals(dateOut) ||
				dateOfBooking.after(dateIn) || dateOfBooking.equals(dateIn) ) {
			errorMessage = "DateOut cant be less then or equal dateIn,"
							+ " dateIn cant be less then or equal current date";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		BookingDAO bookingDAO = new BookingDAO();
		Booking booking = new Booking(user, room, bookingStatus, dateIn, dateOut, dateOfBooking);
		log.trace("Booking to create --> " + booking);

		bookingDAO.createBooking(booking);

		log.debug("Command ends");
		return redirect;
	}

}
