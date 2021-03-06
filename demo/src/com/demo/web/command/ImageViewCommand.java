/**
 *
 */
package com.demo.web.command;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;

import com.demo.db.dao.ImageDAO;
import com.demo.db.entity.Image;
import com.demo.web.constants.Path;

/**
 * Image view.
 *
 * @author A.Serbin
 *
 */
public class ImageViewCommand extends Command {

	private static final long serialVersionUID = 6322143108140190894L;
	private static final Logger log = Logger.getLogger(ImageViewCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

 		String errorMessage = null;
 		String uri = Path.PAGE__ERROR;

		Image image = null;
		try {
			Long imageId = Long.parseLong(request.getParameter("image_id"));
	 		image = new ImageDAO().findImageById(imageId);
		} catch (NumberFormatException e) {
			errorMessage = "Invalid image id format.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

		if(image == null) {
			errorMessage = "No such image.";
			log.error("errorMessage --> " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return uri;
		}

 		response.setContentType("image/jpeg");
 		InputStream inputStream = new ByteArrayInputStream(image.getData());
 		IOUtils.copy(inputStream, response.getOutputStream());

 		uri = "image";

		log.debug("Command finished.");
		return uri;
	}

}
