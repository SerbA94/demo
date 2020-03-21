package com.demo.web.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.entity.Role;
import com.demo.web.constants.Path;


public class CommandAccessFilter implements Filter {

	private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

	// commands access
	private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private static List<String> commons = new ArrayList<String>();
	private static List<String> outOfControl = new ArrayList<String>();

	public void destroy() {
		log.debug("Filter destruction starts");
		// do nothing
		log.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("Filter starts");

		if (accessAllowed(request)) {
			log.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessasge);
			log.trace("Set the request attribute: errorMessage --> " + errorMessasge);

			log.debug("Forvard to Path.PAGE__ERROR_PAGE");

			request.getRequestDispatcher(Path.COMMAND__VIEW_ERROR).forward(request, response);
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		HttpSession session = httpRequest.getSession(true);
		String commandName = request.getParameter("command");
		log.trace("Got command --> " + commandName);

		Role userRole = (Role)session.getAttribute("userRole");

		if (userRole == null) {
			if (outOfControl.contains(commandName)) {
				return true;
			}
			return false;
		}
		return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		log.debug("Filter initialization starts");

		// roles
		accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
		accessMap.put(Role.MANAGER, asList(fConfig.getInitParameter("manager")));
		accessMap.put(Role.CUSTOMER, asList(fConfig.getInitParameter("customer")));
		accessMap.put(Role.INACTIVE, asList(fConfig.getInitParameter("inactive")));
		log.trace("Access map --> " + accessMap);

		// commons
		commons = asList(fConfig.getInitParameter("common"));
		log.trace("Common commands --> " + commons);

		// out of control
		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		log.trace("Out of control commands --> " + outOfControl);

		log.debug("Filter initialization finished");
	}

	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) list.add(st.nextToken());
		return list;
	}

}