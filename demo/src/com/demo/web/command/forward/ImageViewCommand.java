package com.demo.web.command.forward;

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
import com.demo.web.command.Command;


public class ImageViewCommand extends Command {

	private static final long serialVersionUID = 6322143108140190894L;

	private static final Logger log = Logger.getLogger(ImageViewCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
 		response.setContentType("image/jpeg");

		ImageDAO imageDAO = new ImageDAO();

		Long imageId = Long.parseLong(request.getParameter("image_id"));
		log.trace("Request parameter: image_id --> " + imageId);

 		Image image = imageDAO.findImageById(imageId);

 		InputStream inputStream = new ByteArrayInputStream(image.getData());
 		IOUtils.copy(inputStream, response.getOutputStream());

		log.debug("Command finished");
		return null;
	}

}
