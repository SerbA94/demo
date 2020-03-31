/**
 *
 */
package com.demo.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.RoomDAO;
import com.demo.db.entity.Description;
import com.demo.db.entity.Room;
import com.demo.db.entity.RoomStatus;
import com.demo.db.entity.User;
import com.demo.web.constants.Path;

/**
 * Room view.
 *
 * @author A.Serbin
 *
 */
public class RoomViewCommand extends Command {

	private static final long serialVersionUID = 1741486663765168907L;
	private static final Logger log = Logger.getLogger(RoomViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String uri = Path.PAGE__ERROR;
		String errorMessage = null;

		User user = (User) request.getSession().getAttribute("user");
		log.trace("User from session --> " + user);

		Long id = null;
		Room room = null;
		try {
			id = Long.parseLong(request.getParameter("room_id"));
			room = new RoomDAO().findRoomById(id);
		} catch (NumberFormatException e) {
			errorMessage = "Invalid id format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		if(room == null) {
			errorMessage = "No room with id : id --> " + id;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		if(room.getRoomStatus().contains(RoomStatus.INACCESSIBLE)) {
			errorMessage = "Room inaccesible : room number --> " + room.getNumber();
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		uri = Path.PAGE__CUSTOMER_ROOM;

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

		log.trace("Room sent on view : number --> " + room.getNumber());
		request.setAttribute("room", room);

		log.debug("Command finished.");
		return uri;
	}
}
