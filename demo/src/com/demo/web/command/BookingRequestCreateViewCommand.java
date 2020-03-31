/**
 *
 */
package com.demo.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.db.dao.RoomClassDAO;
import com.demo.db.dao.RoomDAO;
import com.demo.db.entity.RoomClass;
import com.demo.web.constants.Path;
import com.demo.web.utils.TimestampUtil;

/**
 * Booking request create view.
 *
 * @author A.Serbin
 *
 */
public class BookingRequestCreateViewCommand extends Command {

	private static final long serialVersionUID = -7670311821858038907L;
	private static final Logger log = Logger.getLogger(BookingRequestCreateViewCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Command started.");

		Integer maxCapacity = new RoomDAO().findMaxFreeRoomCapacity();
		log.trace("maxCapacity sent on view --> " + maxCapacity);
		request.setAttribute("maxCapacity", maxCapacity);

		List<RoomClass> roomClasses = new RoomClassDAO().findAllRoomClasses();
		log.trace("roomClasses sent on view --> " + roomClasses);
		request.setAttribute("roomClasses", roomClasses);

		String nextDateIn = TimestampUtil.getNextDateIn("yyyy-MM-dd");
		log.trace("Next date in : nextDateIn --> " + nextDateIn);
		request.setAttribute("nextDateIn", nextDateIn);

		String nextDateOut = TimestampUtil.getNextDateOut("yyyy-MM-dd");
		log.trace("Next date out : nextDateOut --> " + nextDateOut);
		request.setAttribute("nextDateOut", nextDateOut);

		log.debug("Command finished.");
		return Path.PAGE__CUSTOMER_BOOKING_REQUEST_CREATE;
	}

}
