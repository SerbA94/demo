package com.demo.web.command.forward;

import java.io.IOException;
import java.util.Arrays;

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

		String[] sortingFields = new String[]{"capacity","room_class_title","price","room_status_title"};

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		log.trace("user from session --> " + user);

		String orderBy = request.getParameter("orderBy");
		log.trace("Request parameter : orderBy --> " + orderBy);

		orderBy = orderBy != null && Arrays.asList(sortingFields).contains(orderBy) ? orderBy : null;

		if(user != null && (Role) user.getRole().toArray()[0] == Role.ADMIN) {
			log.trace("userRole --> " + (Role) user.getRole().toArray()[0]);

			if(orderBy != null) {
				request.setAttribute("rooms", new RoomDAO().findAllOrderedRooms(orderBy));
				request.setAttribute("orderBy", orderBy);
				log.debug("Command finished");
				return Path.PAGE__ADMIN_ROOM_LIST;
			}else {
				request.setAttribute("rooms", new RoomDAO().findAllRooms());
				log.debug("Command finished");
				return Path.PAGE__ADMIN_ROOM_LIST;
			}
		}

		if(orderBy != null) {
			request.setAttribute("rooms", new RoomDAO().findAllFreeOrderedRooms(orderBy));
			request.setAttribute("orderBy", orderBy);
			log.debug("Command finished");
			return Path.PAGE__CUSTOMER_ROOM_LIST;
		}else {
			request.setAttribute("rooms", new RoomDAO().findAllFreeRooms());
			log.debug("Command finished");
			return Path.PAGE__CUSTOMER_ROOM_LIST;
		}

	}

}