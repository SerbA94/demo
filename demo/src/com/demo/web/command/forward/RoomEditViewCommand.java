/**
 *
 */
package com.demo.web.command.forward;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.RoomClassDAO;
import com.demo.db.dao.RoomDAO;
import com.demo.db.dao.RoomStatusDAO;
import com.demo.db.entity.Role;
import com.demo.db.entity.Room;
import com.demo.db.entity.RoomClass;
import com.demo.db.entity.RoomStatus;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

/**
 * Edit room view.
 *
 * @author A.Serbin
 *
 */
public class RoomEditViewCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;
	private static final Logger log = Logger.getLogger(RoomEditViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String errorMessage = null;
		String uri = Path.PAGE__ERROR;

		User user = (User) request.getSession().getAttribute("user");
		log.trace("User from session --> " + user);

		if(user == null || !user.getRole().contains(Role.ADMIN) ) {
			errorMessage = "You do not have permission to access the requested resource.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		Room room = null;
		Long roomId = null;
		try {
			roomId = Long.parseLong(request.getParameter("edit_room_id"));;
			room  = new RoomDAO().findRoomById(roomId);
		} catch (NumberFormatException e) {
			errorMessage = "Invalid room id format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		if(room == null) {
			errorMessage = "No such room : id --> " + roomId;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		request.setAttribute("room", room);
		log.trace("Room to edit sent on view : id --> " + room.getId());

		List<RoomStatus> roomStatuses = new RoomStatusDAO().findAllRoomStatuses();
		log.trace("roomStatuses sent on view --> " + roomStatuses);
		request.setAttribute("roomStatuses", roomStatuses);

		List<RoomClass> roomClasses = new RoomClassDAO().findAllRoomClasses();
		log.trace("roomClasses sent on view --> " + roomClasses);
		request.setAttribute("roomClasses", roomClasses);

		uri = Path.PAGE__ADMIN_ROOM_EDIT;

		log.debug("Command finished.");
		return uri;
	}
}
