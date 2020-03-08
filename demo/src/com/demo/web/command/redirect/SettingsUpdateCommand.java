package com.demo.web.command.redirect;


import org.apache.log4j.Logger;

import com.demo.db.dao.UserDao;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

public class SettingsUpdateCommand extends Command implements Redirector{

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger log = Logger.getLogger(SettingsUpdateCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		log.debug("Command starts");

		// UPDATE USER ////////////////////////////////////////////////////////

		User user = (User)request.getSession().getAttribute("user");
		boolean updateUser = false;


		String localeToSet = request.getParameter("localeToSet");
		if (localeToSet != null && !localeToSet.isEmpty()) {
			HttpSession session = request.getSession();
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);
			session.setAttribute("defaultLocale", localeToSet);
			user.setLocaleName(localeToSet);
			updateUser = true;
		}

		if (updateUser == true) {
			new UserDao().updateUser(user);
		}

		log.debug("Command finished");
		return Path.COMMAND__VIEW_SETTINGS;
	}

}