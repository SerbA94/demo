/**
 *
 */
package com.demo.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.web.constants.Path;

/**
 * Settings view.
 *
 * @author A.Serbin
 *
 */
public class SettingsViewCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;
	private static final Logger log = Logger.getLogger(SettingsViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command started.");

		log.debug("Command finished.");
		return Path.PAGE__SETTINGS;
	}

}