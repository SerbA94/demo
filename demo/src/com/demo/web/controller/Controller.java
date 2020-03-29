package com.demo.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.web.command.Command;
import com.demo.web.command.CommandContainer;
import com.demo.web.constants.Path;

@MultipartConfig(maxFileSize = 16177215)
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 2423353715955164816L;
	private static final Logger log = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		log.debug("Controller started.");

		String commandName = request.getParameter("command");
		log.trace("Request parameter: command --> " + commandName);

		Command command = CommandContainer.get(commandName);
		log.trace("Obtained command --> " + command);

		String address = command.execute(request, response);
		log.trace("Address --> " + address);

		if(address == null || address.isEmpty()) {
			String errorMessage = "Command execution result can't be empty or null.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			log.debug("Controller finished, now go to redirect address --> " + Path.COMMAND__VIEW_ERROR);
			response.sendRedirect(request.getContextPath() + Path.COMMAND__VIEW_ERROR);
		}

		if(address.equals("image")) {
			log.debug("Controller finished, image loaded.");
		}else if(address.contains(Path.PAGE)){
			log.debug("Controller finished, now go to forward address --> " + address);
			RequestDispatcher disp = request.getRequestDispatcher(address);
			disp.forward(request, response);
		}else {
			log.debug("Controller finished, now go to redirect address --> " + address);
			response.sendRedirect(request.getContextPath() + address);
		}
	}
}