package com.demo.web.command.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.dao.RoomDAO;
import com.demo.db.entity.Role;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class RoomListViewCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger log = Logger.getLogger(RoomListViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		log.trace("user from session --> " + user);

		if(user != null) {
			Role userRole = (Role) user.getRole().toArray()[0];
			log.trace("userRole --> " + userRole);

			if (userRole == Role.ADMIN) {
				request.setAttribute("rooms", new RoomDAO().findAllRooms());
				log.debug("Command finished");
				return Path.PAGE__ADMIN_ROOMS;
			}else if(userRole == Role.CUSTOMER){
				request.setAttribute("rooms", new RoomDAO().findAllAccessibleRooms());
				log.debug("Command finished");
				return Path.PAGE__CUSTOMER_ROOMS;
			}
		}
		errorMessage = "You do not have permission to access the requested resource.  ";
		request.setAttribute("errorMessage", errorMessage);
		log.error("errorMessage --> " + errorMessage);

		log.debug("Command finished");
		return forward;
	}

}