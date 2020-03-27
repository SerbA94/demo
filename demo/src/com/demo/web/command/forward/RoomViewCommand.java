package com.demo.web.command.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.dao.RoomDAO;
import com.demo.db.entity.Description;
import com.demo.db.entity.Room;
import com.demo.db.entity.RoomStatus;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class RoomViewCommand extends Command {

	private static final long serialVersionUID = 1741486663765168907L;
	private static final Logger log = Logger.getLogger(RoomViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String forward = Path.PAGE__CUSTOMER_ROOM;
		String errorMessage = null;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		log.trace("user from session --> " + user);

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

		RoomStatus roomStatus = (RoomStatus) room.getRoomStatus().toArray()[0];
		if(roomStatus.equals(RoomStatus.INACCESSIBLE)) {
			errorMessage = "Room inaccesible : room number --> " + room.getNumber();
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			forward = Path.PAGE__ERROR;
			return forward;
		}

		String localeName = null;
		if(user!=null && user.getLocaleName()!=null) {
			localeName = user.getLocaleName();
		} else {
			localeName = "ru";
		}
		for (Description description : room.getDescriptions()) {
			if(description.getLocaleName().equals(localeName)) {
				log.trace("Description locale sent on view : localeName --> " + localeName);
				request.setAttribute("description", description.getDescription());
			}
		}

		log.trace("Room from db  sent on view --> " + room);
		request.setAttribute("room", room);
		log.debug("Command finished");
		return forward;
	}
}
