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
 * Room edit command.
 *
 * @author A.Serbin
 *
 */
public class RoomEditCommand extends Command {

	private static final long serialVersionUID = -5392301052207392889L;
	private static final Logger log = Logger.getLogger(RoomEditCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String uri = Path.PAGE__ERROR;
		String errorMessage = null;

		RoomDAO roomDAO = new RoomDAO();

		String roomIdParam = request.getParameter("edit_room_id").trim();
		log.trace("Request parameter: edit_room_id --> " + roomIdParam);

		Room room = null;
		try {
			Long roomId = Long.parseLong(roomIdParam);
			room  = roomDAO.findRoomById(roomId);
		} catch (NumberFormatException e) {
			errorMessage = "Invalid room id format : id --> " + roomIdParam;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}
		if(room == null) {
			errorMessage = "No room with id: edit_room_id  --> " + roomIdParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		String numberParam = request.getParameter("number").trim();
		log.trace("Request parameter: number --> " + numberParam);
		try {
			Integer number = null;
			if(numberParam != null && !numberParam.isEmpty()){
				number = Integer.parseInt(numberParam);
			}
			if(number != null && !number.equals(room.getNumber())) {
				log.trace("Room setNumber: number --> " + number);
				room.setNumber(number);
			}
		} catch (NumberFormatException e) {
			errorMessage = "Invalid number : number --> " + numberParam;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return new RoomEditViewCommand().execute(request, response);
		}

		String capacityParam = request.getParameter("capacity").trim();
		log.trace("Request parameter: capacity --> " + capacityParam);
		try {
			Integer capacity = null;
			if(capacityParam != null && !capacityParam.isEmpty()){
				capacity = Integer.parseInt(capacityParam);
			}
			if(capacity != null && !capacity.equals(room.getCapacity())) {
				log.trace("Room setCapacity: capacity --> " + capacity);
				room.setCapacity(capacity);
			}
		} catch (NumberFormatException e) {
			errorMessage = "Invalid capacity : capacity --> " + capacityParam;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return new RoomEditViewCommand().execute(request, response);
		}

		String priceParam = request.getParameter("price").trim();
		log.trace("Request parameter: price --> " + priceParam);
		try {
			Integer price = null;
			if(priceParam != null && !priceParam.isEmpty()){
				price = Integer.parseInt(priceParam);
			}
			if(price != null && !price.equals(room.getPrice())) {
				log.trace("Room setPrice: price --> " + price);
				room.setPrice(price);
			}
		} catch (NumberFormatException e) {
			errorMessage = "Invalid price : price --> " + priceParam;
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return new RoomEditViewCommand().execute(request, response);
		}

		String roomClassParam = request.getParameter("roomClass").trim();
		log.trace("Request parameter: roomClass --> " + roomClassParam);
		Set<RoomClass> roomClass = RoomClassDAO.getRoomClassSet(roomClassParam);
		if(roomClass == null && roomClassParam != null && !roomClassParam.isEmpty() ) {
			errorMessage = "Room class not exists : roomClassParam --> " + roomClassParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new RoomEditViewCommand().execute(request, response);
		}
		room.setRoomClass(roomClass);

		String roomStatusParam = request.getParameter("roomStatus").trim();
		log.trace("Request parameter: roomStatus --> " + roomStatusParam);
		Set<RoomStatus> roomStatus = RoomStatusDAO.getRoomStatusSet(roomStatusParam);
		if(roomStatus == null && roomStatusParam != null && !roomStatusParam.isEmpty() ) {
			errorMessage = "Room status not exists : roomStatusParam --> " + roomStatusParam;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return new RoomEditViewCommand().execute(request, response);
		}
		room.setRoomStatus(roomStatus);

		List<Description> descriptions = new ArrayList<>();

		String description_ru = request.getParameter("description_ru").trim();
		log.trace("Request parameter: description_ru --> " + description_ru);
		if( description_ru != null ) {
			log.trace("Description added: description_ru --> " + description_ru);
			descriptions.add(new Description("ru",description_ru));
		}

		String description_en = request.getParameter("description_en").trim();
		log.trace("Request parameter: description_en --> " + description_en);
		if( description_en != null ) {
			log.trace("Description added: description_en --> " + description_en);
			descriptions.add(new Description("en",description_en));
		}

		if( !descriptions.equals(room.getDescriptions()) ) {
			log.trace("Room setDescriptions: descriptions --> " + descriptions);
			room.setDescriptions(descriptions);
		}

		room = roomDAO.updateRoom(room);
		if(room == null) {
			errorMessage = "Room updating failed : room was not updated.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return uri;
		}

		log.trace("Room with id updated: id --> " + room.getId());

		uri = Path.COMMAND__VIEW_ROOM_LIST;

		log.debug("Command finished.");
		return uri;
	}
}