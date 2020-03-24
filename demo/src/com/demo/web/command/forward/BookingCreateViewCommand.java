package com.demo.web.command.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.RoomDAO;
import com.demo.db.entity.Room;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class BookingCreateViewCommand extends Command {

	private static final long serialVersionUID = -2582379197846722054L;
	private static final Logger log = Logger.getLogger(BookingCreateViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String forward = Path.PAGE__CUSTOMER_BOOKING_CREATE;
		String errorMessage = null;

		Long id = null;
		try {
			id = Long.parseLong(request.getParameter("room_id"));
		} catch (NumberFormatException e) {
			errorMessage = "Invalid id format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			forward = Path.PAGE__ERROR;
			return forward;
		}
		log.trace("Room id from request --> " + id);

		Room room = new RoomDAO().findRoomById(id);
		if(room == null) {
			errorMessage = "No room with id : id --> " + id;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			forward = Path.PAGE__ERROR;
			return forward;
		}

		log.trace("Room from db  sent on view --> " + room);
		request.setAttribute("room", room);
		log.debug("Command finished");
		return forward;
	}

}
