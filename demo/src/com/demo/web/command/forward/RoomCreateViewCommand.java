package com.demo.web.command.forward;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.dao.RoomClassDAO;
import com.demo.db.dao.RoomStatusDAO;
import com.demo.db.entity.Role;
import com.demo.db.entity.RoomClass;
import com.demo.db.entity.RoomStatus;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class RoomCreateViewCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger log = Logger.getLogger(RoomCreateViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		String errorMessage = null;
		String forward = Path.COMMAND__VIEW_ERROR;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		log.trace("user from session --> " + user);

		if(user != null) {
			Role userRole = (Role) user.getRole().toArray()[0];
			log.trace("userRole --> " + userRole);

			if (userRole == Role.ADMIN) {
				List<RoomStatus> roomStatuses = new RoomStatusDAO().findAllRoomStatuses();
				log.trace("roomStatuses sent on view --> " + roomStatuses);
				request.setAttribute("roomStatuses", roomStatuses);

				List<RoomClass> roomClasses = new RoomClassDAO().findAllRoomClasses();
				log.trace("roomClasses sent on view --> " + roomClasses);
				request.setAttribute("roomClasses", roomClasses);

				log.debug("Command finished");
				return Path.PAGE__ADMIN_ROOM_CREATE;
			}
		}
		errorMessage = "You do not have permission to access the requested resource.";
		request.setAttribute("errorMessage", errorMessage);
		log.error("errorMessage --> " + errorMessage);

		log.debug("Command finished");
		return forward;
	}

}