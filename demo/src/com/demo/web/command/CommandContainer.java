package com.demo.web.command;


import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.demo.web.command.forward.AccountViewCommand;
import com.demo.web.command.forward.ActivationViewCommand;
import com.demo.web.command.forward.BookingRequestCreateViewCommand;
import com.demo.web.command.forward.BookingRequestListViewCommand;
import com.demo.web.command.forward.BookingRequestViewCommand;
import com.demo.web.command.forward.BookingCreateViewCommand;
import com.demo.web.command.forward.ErrorViewCommand;
import com.demo.web.command.forward.ImageViewCommand;
import com.demo.web.command.forward.LoginViewCommand;
import com.demo.web.command.forward.NoCommand;
import com.demo.web.command.forward.RegistrationViewCommand;
import com.demo.web.command.forward.RoomCreateViewCommand;
import com.demo.web.command.forward.RoomEditViewCommand;
import com.demo.web.command.forward.RoomListViewCommand;
import com.demo.web.command.forward.RoomViewCommand;
import com.demo.web.command.forward.SettingsViewCommand;
import com.demo.web.command.forward.WelcomeViewCommand;
import com.demo.web.command.redirect.ActivationCommand;
import com.demo.web.command.redirect.ActivationMailCommand;
import com.demo.web.command.redirect.BookingRequestCreateCommand;
import com.demo.web.command.redirect.ImageDeleteCommand;
import com.demo.web.command.redirect.ImageUploadCommand;
import com.demo.web.command.redirect.LoginCommand;
import com.demo.web.command.redirect.LogoutCommand;
import com.demo.web.command.redirect.RegistrationCommand;
import com.demo.web.command.redirect.RoomCreateCommand;
import com.demo.web.command.redirect.RoomEditCommand;
import com.demo.web.command.redirect.SettingsUpdateCommand;


public class CommandContainer {

	private static final Logger log = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {

		// commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("no-command", new NoCommand());
		commands.put("update-settings", new SettingsUpdateCommand());
		commands.put("registration", new RegistrationCommand());
		commands.put("create-room", new RoomCreateCommand());
		commands.put("edit-room", new RoomEditCommand());
		commands.put("upload-image", new ImageUploadCommand());
		commands.put("delete-image", new ImageDeleteCommand());
		commands.put("activation", new ActivationCommand());
		commands.put("activation-mail", new ActivationMailCommand());
		commands.put("create-booking-request", new BookingRequestCreateCommand());

		// view commands
		commands.put("view-activation", new ActivationViewCommand());
		commands.put("view-settings", new SettingsViewCommand());
		commands.put("view-registration", new RegistrationViewCommand());
		commands.put("view-welcome", new WelcomeViewCommand());
		commands.put("view-login", new LoginViewCommand());
		commands.put("view-error", new ErrorViewCommand());
		commands.put("view-room", new RoomViewCommand());
		commands.put("view-room-list", new RoomListViewCommand());
		commands.put("view-room-create", new RoomCreateViewCommand());
		commands.put("view-room-edit", new RoomEditViewCommand());
		commands.put("view-image", new ImageViewCommand());
		commands.put("view-account", new AccountViewCommand());
		commands.put("view-booking-request-create", new BookingRequestCreateViewCommand());
		commands.put("view-booking-request-list", new BookingRequestListViewCommand());
		commands.put("view-booking-request", new BookingRequestViewCommand());
		commands.put("view-booking-create", new BookingCreateViewCommand());

		log.debug("Command container was successfully initialized");
		log.trace("Number of commands --> " + commands.size());
	}

	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}
		return commands.get(commandName);
	}
}