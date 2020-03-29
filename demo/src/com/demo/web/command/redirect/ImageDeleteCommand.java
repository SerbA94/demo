package com.demo.web.command.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.ImageDAO;
import com.demo.db.dao.RoomDAO;
import com.demo.db.entity.Room;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class ImageDeleteCommand extends Command {

	private static final long serialVersionUID = -8890724231959971213L;
	private static final Logger log = Logger.getLogger(ImageDeleteCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String errorMessage = null;
		String link = Path.PAGE__ERROR;

		try {
			Long imageId = Long.parseLong(request.getParameter("image_id"));
			log.trace("Request parameter: image_id --> " + imageId);
			new ImageDAO().deleteImage(imageId);
		} catch (NumberFormatException e) {
			errorMessage = "Invalid image id.";
			log.error(errorMessage);
		}

		try {
			Long roomId = Long.parseLong(request.getParameter("edit_room_id"));
			log.trace("Request parameter: edit_room_id --> " + roomId);
			Room room = new RoomDAO().findRoomById(roomId);
			request.setAttribute("room", room);
			link = Path.COMMAND__VIEW_ROOM_EDIT + "&edit_room_id=" + roomId;
		} catch (NumberFormatException e) {
			errorMessage = "Invalid room id.";
			log.error(errorMessage);
		}

		if(errorMessage != null) {
			request.setAttribute("errorMessage", errorMessage);
		}
		log.debug("Command finished.");
		return link;
	}

}
