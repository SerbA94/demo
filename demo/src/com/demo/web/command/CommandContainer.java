/**
 *
 */
package com.demo.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Holder for all commands.
 *
 * @author A.Serbin
 *
 */
public class CommandContainer {

	private static final Logger log = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {

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
		commands.put("delete-booking-request", new BookingRequestDeleteCommand());
		commands.put("create-booking", new BookingCreateCommand());
		commands.put("bill-mail", new BillMailCommand());
		commands.put("view-bill", new BillViewCommand());
		commands.put("confirm-booking", new BookingConfirmCommand());
		commands.put("delete-booking", new BookingDeleteCommand());

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

	/**
	 * Returns command object with the given name.
	 *
	 * @param commandName
	 *            Name of the command.
	 *
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> " + commandName);
			return commands.get("no-command");
		}
		return commands.get(commandName);
	}
}