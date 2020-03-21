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
import com.demo.web.command.redirect.Redirector;

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

		log.debug("Controller starts");

		// extract command name from the request
		String commandName = request.getParameter("command");
		log.trace("Request parameter: command --> " + commandName);

		// obtain command object by its name
		Command command = CommandContainer.get(commandName);
		log.trace("Obtained command --> " + command);

		// execute command and get address
		String address = command.execute(request, response);
		log.trace("Address --> " + address);


		// if the forward address is not null go to the address
		if (address != null) {
			if(command instanceof Redirector) {
				log.debug("Controller finished, now go to redirect address --> " + address);
				response.sendRedirect(request.getContextPath() + address);
			}else {
				log.debug("Controller finished, now go to forward address --> " + address);
				RequestDispatcher disp = request.getRequestDispatcher(address);
				disp.forward(request, response);
			}
		}
	}

}