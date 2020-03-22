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

public class RoomEditCommand extends Command implements Redirector {

	private static final long serialVersionUID = -5392301052207392889L;
	private static final Logger log = Logger.getLogger(RoomEditCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String redirect = Path.COMMAND__VIEW_ROOM_LIST;
		String errorMessage = null;
		RoomDAO roomDAO = new RoomDAO();

		Long room_id = Long.parseLong(request.getParameter("edit_room_id"));
		log.trace("Request parameter: edit_room_id --> " + room_id);
		Room room  = roomDAO.findRoomById(room_id);
		if(room == null) {
			errorMessage = "No room id db with id: edit_room_id  --> " + room_id;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return Path.COMMAND__VIEW_ERROR;
		}

		Integer number = Integer.parseInt(request.getParameter("number"));
		log.trace("Request parameter: number --> " + number);
		if( number != null && !number.equals(room.getNumber()) ) {
			log.trace("Room setNumber: number --> " + number);
			room.setNumber(number);
		}

		Integer capacity = Integer.parseInt(request.getParameter("capacity"));
		log.trace("Request parameter: capacity --> " + capacity);
		if( capacity != null && !capacity.equals(room.getCapacity()) ) {
			log.trace("Room setCapacity: capacity --> " + capacity);
			room.setCapacity(capacity);
		}

		Integer price = Integer.parseInt(request.getParameter("price"));
		log.trace("Request parameter: price --> " + price);
		if( price != null && !price.equals(room.getPrice()) ) {
			log.trace("Room setPrice: price --> " + price);
			room.setPrice(price);
		}

		String roomStatusStr = request.getParameter("roomStatus");
		log.trace("Request parameter: roomStatus --> " + roomStatusStr);
		Set<RoomStatus> roomStatus = RoomStatusDAO.getRoomStatusSet(roomStatusStr);
		if( roomStatus != null && !roomStatus.equals(room.getRoomStatus()) ) {
			log.trace("Room setRoomStatus: roomStatus --> " + roomStatusStr);
			room.setRoomStatus(roomStatus);
		}

		String roomClassStr = request.getParameter("roomClass");
		log.trace("Request parameter: roomClass --> " + roomClassStr);
		Set<RoomClass> roomClass = RoomClassDAO.getRoomClassSet(roomClassStr);
		if( roomClass != null && !roomClass.equals(room.getRoomClass()) ) {
			log.trace("Room setRoomClass: roomClass --> " + roomClassStr);
			room.setRoomClass(roomClass);
		}

		List<Description> descriptions = new ArrayList<>();

		String description_ru = request.getParameter("description_ru");
		log.trace("Request parameter: description_ru --> " + description_ru);
		if( description_ru != null ) {
			log.trace("Description added: description_ru --> " + description_ru);
			descriptions.add(new Description("ru",description_ru));
		}

		String description_en = request.getParameter("description_en");
		log.trace("Request parameter: description_en --> " + description_en);
		if( description_en != null ) {
			log.trace("Description added: description_en --> " + description_en);
			descriptions.add(new Description("en",description_en));
		}

		if( !descriptions.equals(room.getDescriptions()) ) {
			log.trace("Room setDescriptions: descriptions --> " + descriptions);
			room.setDescriptions(descriptions);
		}

		roomDAO.updateRoom(room);
		log.trace("Room with id updated: id --> " + room.getId());
		log.debug("Command ends");
		return redirect;

	}
}