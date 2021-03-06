/**
 *
 */
package com.demo.web.command;

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
import com.demo.web.constants.Path;

/**
 * Room create command.
 *
 * @author A.Serbin
 *
 */
public class RoomCreateCommand extends Command {

	private static final long serialVersionUID = -446157167371195998L;
	private static final Logger log = Logger.getLogger(RoomCreateCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String errorMessage = null;
		String uri = Path.PAGE__ERROR;

		String capacityParam = request.getParameter("capacity");
		log.trace("Request parameter: capacity --> " + capacityParam);

		String priceParam = request.getParameter("price");
		log.trace("Request parameter: price --> " + priceParam);

		String numberParam = request.getParameter("number");
		log.trace("Request parameter: number --> " + numberParam);

		Integer capacity = null;
		Integer price = null;
		Integer number = null;
		try {
			capacity = Integer.parseInt(capacityParam);
			price = Integer.parseInt(priceParam);
			number = Integer.parseInt(numberParam);
		} catch (NumberFormatException e) {
			errorMessage = "Room creation failed : Invalid integer format : capacity/price/number --> "
					+ capacityParam + "/" + priceParam + "/" + numberParam;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return new RoomCreateViewCommand().execute(request, response);
		}

		String roomStatusParam = request.getParameter("roomStatus");
		log.trace("Request parameter: roomStatus --> " + roomStatusParam);
		Set<RoomStatus> roomStatus = RoomStatusDAO.getRoomStatusSet(roomStatusParam);
		if(roomStatus == null && (roomStatusParam == null || roomStatusParam.isEmpty())) {
			errorMessage = "Room creation failed : No such room status : "
					+ "roomStatusParam --> " + roomStatusParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new RoomCreateViewCommand().execute(request, response);
		}

		String roomClassParam = request.getParameter("roomClass");
		log.trace("Request parameter: roomClass --> " + roomClassParam);
		Set<RoomClass> roomClass = RoomClassDAO.getRoomClassSet(roomClassParam);
		if(roomClass == null && (roomClassParam == null || roomClassParam.isEmpty())) {
			errorMessage = "Room creation failed : No such room class : "
					+ "roomClassParam --> " + roomClassParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new RoomCreateViewCommand().execute(request, response);
		}

		String description_ru = request.getParameter("description_ru");
		log.trace("Request parameter: description_ru --> " + description_ru);

		String description_en = request.getParameter("description_en");
		log.trace("Request parameter: description_en --> " + description_en);

		if(description_ru == null || description_en == null) {
			errorMessage = "Room creation failed : description can't be null.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new RoomCreateViewCommand().execute(request, response);
		}

		List<Description> descriptions = new ArrayList<>();
		descriptions.add(new Description("ru",description_ru));
		descriptions.add(new Description("en",description_en));

		Room room = new Room(number,capacity,price,null,roomStatus,roomClass,descriptions);
		room = new RoomDAO().createRoom(room);
		if(room == null) {
			errorMessage = "Room creation failed : room was not created.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}
		log.trace("Room created successfuly : id --> " + room.getId());

		uri = Path.COMMAND__VIEW_ROOM_LIST;

		log.debug("Command finished.");
		return uri;
	}

}
