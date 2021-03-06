/**
 *
 */
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

/**
 * Security filter.
 *
 * @author A.Serbin
 *
 */
public class CommandAccessFilter implements Filter {

	private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

	private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private static List<String> commons = new ArrayList<String>();
	private static List<String> outOfControl = new ArrayList<String>();

	public void destroy() {
		log.debug("Filter destruction starts");

		log.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("Filter starts");

		if (accessAllowed(request)) {
			log.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession(true);
			Role userRole = (Role)session.getAttribute("userRole");
			log.trace("Role from session : userRole --> " + userRole);

			String uri = Path.PAGE__ERROR;
			String errorMessasge = "You do not have permission to access the requested resource.";

			if (userRole != null) {
				if(userRole == Role.INACTIVE) {
					uri = Path.PAGE__INACTIVE_ACTIVATION;
					errorMessasge = errorMessasge + "Please activate your account.";
				}
			}else {
				uri = Path.PAGE__LOGIN;
				errorMessasge = errorMessasge + "Please login.";
			}

			request.setAttribute("errorMessage", errorMessasge);
			log.trace("Set the request attribute: errorMessage --> " + errorMessasge);
			log.debug("Forvard to " + uri);

			request.getRequestDispatcher(uri).forward(request, response);
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

		accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
		accessMap.put(Role.MANAGER, asList(fConfig.getInitParameter("manager")));
		accessMap.put(Role.CUSTOMER, asList(fConfig.getInitParameter("customer")));
		accessMap.put(Role.INACTIVE, asList(fConfig.getInitParameter("inactive")));
		log.trace("Access map --> " + accessMap);

		commons = asList(fConfig.getInitParameter("common"));
		log.trace("Common commands --> " + commons);

		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		log.trace("Out of control commands --> " + outOfControl);

		log.debug("Filter initialization finished");
	}

	/**
	 * Extracts parameter values from string.
	 *
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) list.add(st.nextToken());
		return list;
	}

}