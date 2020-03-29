package com.demo.web.command.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.demo.db.dao.ImageDAO;
import com.demo.db.entity.Image;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;


public class ImageUploadCommand extends Command {

	private static final long serialVersionUID = 4854668554533486764L;
	private static final Logger log = Logger.getLogger(ImageUploadCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		String link = Path.PAGE__ERROR;
		String errorMessage = null;

		Long roomId = null;
		try {
			roomId = Long.parseLong(request.getParameter("edit_room_id"));
			log.trace("Request parameter: edit_room_id --> " + roomId);
		} catch (NumberFormatException e) {
			errorMessage = "Invalid room id format : id --> " + roomId;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return link;
		}

		Part part  = request.getPart("image");
		if(part == null) {
			errorMessage = "No image to upload.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return link;
		}else {
			log.debug("Got file to upload : name/size --> "
					+ part.getSubmittedFileName() + "/" + part.getSize());
		}

		byte[] data = IOUtils.toByteArray(part.getInputStream());

		String name = part.getSubmittedFileName();

		Image image = new Image(name,data,roomId);
		log.trace("Image to upload : image --> " + image);

		image = new ImageDAO().createImage(image);
		if(image == null || image.getId() == null) {
			errorMessage = "Image creation failed : Image was not created.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return link;
		}
		log.trace("Image successfuly created : id --> " + image.getId());

		link = Path.COMMAND__VIEW_ROOM_EDIT + "&edit_room_id=" + roomId;
		log.debug("Command finished.");
		return link;
	}

}
