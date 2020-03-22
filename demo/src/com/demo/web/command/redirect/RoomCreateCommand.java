package com.demo.web.command.redirect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.RoomClassDAO;
import com.demo.db.dao.RoomDAO;
import com.demo.db.dao.RoomStatusDAO;
import com.demo.db.entity.Description;
import com.demo.db.entity.Room;
import com.demo.db.entity.RoomClass;
import com.demo.db.entity.RoomStatus;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class RoomCreateCommand extends Command implements Redirector {

	private static final long serialVersionUID = -446157167371195998L;
	private static final Logger log = Logger.getLogger(RoomCreateCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String redirect = Path.COMMAND__VIEW_ROOM_LIST;
		RoomDAO roomDAO = new RoomDAO();

		Integer number = Integer.parseInt(request.getParameter("number"));
		Integer capacity = Integer.parseInt(request.getParameter("capacity"));
		Integer price = Integer.parseInt(request.getParameter("price"));

		String roomStatusStr = request.getParameter("roomStatus");
		log.trace("Request parameter: roomStatus --> " + roomStatusStr);
		Set<RoomStatus> roomStatus = RoomStatusDAO.getRoomStatusSet(roomStatusStr);

		String roomClassStr = request.getParameter("roomClass");
		log.trace("Request parameter: roomClass --> " + roomClassStr);
		Set<RoomClass> roomClass = RoomClassDAO.getRoomClassSet(roomClassStr);

		List<Description> descriptions = new ArrayList<>();
		descriptions.add(new Description("ru",request.getParameter("description_ru")));
		descriptions.add(new Description("en",request.getParameter("description_en")));


		Room room = new Room(number,capacity,price,null,roomStatus,roomClass,descriptions);

		roomDAO.createRoom(room);

		log.debug("Command ends");
		return redirect;
	}

}
