package com.demo.web.command.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.RoomDAO;
import com.demo.db.entity.Room;
import com.demo.db.entity.RoomStatus;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class BookingCreateViewCommand extends Command {

	private static final long serialVersionUID = -2582379197846722054L;
	private static final Logger log = Logger.getLogger(BookingCreateViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String link = Path.PAGE__ERROR;
		String errorMessage = null;

		Long id = null;
		try {
			id = Long.parseLong(request.getParameter("room_id"));
		} catch (NumberFormatException e) {
			errorMessage = "Invalid id format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return link;
		}
		log.trace("Room id from request --> " + id);

		Room room = new RoomDAO().findRoomById(id);
		if(room == null) {
			errorMessage = "No room with id : id --> " + id;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return link;
		}

		if(!room.getRoomStatus().contains(RoomStatus.FREE)) {
			errorMessage = "Room cant be booked : room number --> " + room.getNumber();
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return link;
		}

		link = Path.PAGE__CUSTOMER_BOOKING_CREATE;
		log.trace("Room sent on view : number --> " + room.getNumber());
		request.setAttribute("room", room);

		log.debug("Command finished.");
		return link;
	}

}
