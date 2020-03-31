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
import com.demo.db.dao.RoomDAO;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.Room;
import com.demo.db.entity.RoomClass;
import com.demo.web.constants.Path;

/**
 * Booking request view.
 *
 * @author A.Serbin
 *
 */
public class BookingRequestViewCommand extends Command {

	private static final long serialVersionUID = -7670311821858038907L;
	private static final Logger log = Logger.getLogger(BookingRequestViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String uri = Path.PAGE__ERROR;
		String errorMessage = null;

		Long id = null;
		try {
			id = Long.parseLong(request.getParameter("booking_request_id"));
		} catch (NumberFormatException e) {
			errorMessage = "Invalid id format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}
		log.trace("Booking request id from request --> " + id);

		BookingRequestDAO bookingRequestDAO = new BookingRequestDAO();
		BookingRequest bookingRequest = bookingRequestDAO.findBookingRequestById(id);
		if(bookingRequest == null) {
			errorMessage = "No booking request with id : id --> " + id;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		uri = Path.PAGE__MANAGER_BOOKING_REQUEST;

		List<Room> rooms = new RoomDAO().findFreeFilteredRooms(
				bookingRequest.getCapacity(), (RoomClass)bookingRequest.getRoomClass().toArray()[0]);
		log.trace("Rooms amount sent on view : amount --> " + rooms.size());

		request.setAttribute("bookingRequest", bookingRequest);
		request.setAttribute("rooms", rooms);

		log.debug("Command finished.");
		return uri;
	}

}
