package com.demo.web.command.redirect;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.demo.db.dao.BookingRequestDAO;
import com.demo.db.dao.RoomClassDAO;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.RoomClass;
import com.demo.db.entity.User;
import com.demo.web.command.Command;
import com.demo.web.constants.Path;

public class BookingRequestCreateCommand extends Command implements Redirector {

	private static final long serialVersionUID = 6937732969299796074L;

	private static final Logger log = Logger.getLogger(BookingRequestCreateCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		String redirect = Path.COMMAND__VIEW_ACCOUNT;
		String errorMessage = null;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		log.trace("user from session --> " + user);

		String capacityStr = request.getParameter("capacity");
		log.trace("Request parameter: capacity --> " + capacityStr);
		Integer capacity = Integer.parseInt(capacityStr);

		if(capacityStr == null || capacityStr.isEmpty() || capacity == null) {
			errorMessage = "Invalid capacity input format : " + capacityStr;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		String roomClassStr = request.getParameter("roomClass");
		log.trace("Request parameter: roomClass --> " + roomClassStr);
		Set<RoomClass> roomClass = RoomClassDAO.getRoomClassSet(roomClassStr);

		if(roomClassStr == null || roomClassStr.isEmpty() || roomClass == null || roomClass.isEmpty()) {
			errorMessage = "Invalid roomClass input format : " + roomClassStr;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		String dateInStr = request.getParameter("dateIn");
		log.trace("Request parameter: dateIn --> " + dateInStr);
		Timestamp dateIn = null;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateInStr + " 12:00:00");
			dateIn = new Timestamp(date.getTime());
			log.trace("Timestamp dateIn --> " + dateIn);
		} catch (ParseException e) {
			log.error("Date parsing exception.");
		}

		if(dateInStr == null || dateInStr.isEmpty() || dateIn == null) {
			errorMessage = "Invalid dateIn input format : " + dateInStr;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		String dateOutStr = request.getParameter("dateOut");
		log.trace("Request parameter: dateOut --> " + dateOutStr);
		Timestamp dateOut = null;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateOutStr + " 12:00:00");
			dateOut = new Timestamp(date.getTime());
			log.trace("Timestamp dateOut --> " + dateOut);
		} catch (ParseException e) {
			log.error("Date parsing exception.");
		}

		if(dateOutStr == null || dateOutStr.isEmpty() || dateOut == null) {
			errorMessage = "Invalid dateOut input format : " + dateOutStr;
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		if(dateIn.after(dateOut) || dateIn.equals(dateOut)) {
			errorMessage = "DateOut cant be less then or equal dateIn.";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			redirect = Path.COMMAND__VIEW_ERROR;
			return redirect;
		}

		BookingRequest bookingRequest = new BookingRequest(user, capacity, dateIn, dateOut, roomClass);
		BookingRequestDAO bookingRequestDAO = new BookingRequestDAO();
		bookingRequestDAO.createBookingRequest(bookingRequest);

		log.debug("Command ends");
		return redirect;
	}

}
