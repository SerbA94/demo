/**
 *
 */
package com.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.demo.db.DBManager;
import com.demo.db.constants.Fields;
import com.demo.db.entity.BookingRequest;
import com.demo.db.entity.RoomClass;

/**
 * Data access object for BookingRequest entity.
 *
 * @author A.Serbin
 */
public class BookingRequestDAO implements EntityMapper<BookingRequest> {

	private static final String SQL__FIND_BOOKING_REQUEST_BY_ID =
			"SELECT * FROM booking_requests WHERE id=?";
	private static final String SQL__FIND_ALL_BOOKING_REQUESTS =
			"SELECT * FROM booking_requests";
	private static final String SQL__DELETE_BOOKING_REQUEST =
			"DELETE FROM booking_requests WHERE id=?";
	private static final String SQL__CREATE_BOOKING_REQUEST =
			"INSERT INTO booking_requests (user_id, date_in, date_out, capacity,"
			+ " room_class_id) VALUES (?, ?, ?, ?, ?)";


	public BookingRequest findBookingRequestById(Long id) {
		BookingRequest bookingRequest = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_BOOKING_REQUEST_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bookingRequest = mapRow(rs);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			DBManager.getInstance().rollbackAndClose(con);
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return bookingRequest;
	}

	public List<BookingRequest> findAllBookingRequests() {
		List<BookingRequest> bookingRequests = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL__FIND_ALL_BOOKING_REQUESTS);
			while (rs.next()) {
				bookingRequests.add(mapRow(rs));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return bookingRequests;
	}

	public void deleteBookingRequest(BookingRequest bookingRequest) {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__DELETE_BOOKING_REQUEST);
			pstmt.setLong(1, bookingRequest.getId());
			pstmt.executeQuery();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public void createBookingRequest(BookingRequest bookingRequest) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			createBookingRequest(con, bookingRequest);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

    private void createBookingRequest(Connection con, BookingRequest bookingRequest) throws SQLException {
    	PreparedStatement pstmt = con.prepareStatement(SQL__CREATE_BOOKING_REQUEST);

		RoomClassDAO roomClassDAO = new RoomClassDAO();
		Long roomClassId = roomClassDAO.findRoomClassId(
				(RoomClass) bookingRequest.getRoomClass().toArray()[0]);

		int k = 1;
		pstmt.setLong(k++, bookingRequest.getUser().getId());
		pstmt.setTimestamp(k++, bookingRequest.getDateIn());
		pstmt.setTimestamp(k++, bookingRequest.getDateOut());
		pstmt.setInt(k++, bookingRequest.getCapacity());
		pstmt.setLong(k++, roomClassId);
		pstmt.executeUpdate();
		pstmt.close();
    }

	@Override
	public BookingRequest mapRow(ResultSet rs) {
		BookingRequest bookingRequest = new BookingRequest();
		try {
			bookingRequest.setId(rs.getLong(Fields.ENTITY__ID));
			bookingRequest.setCapacity(rs.getInt(Fields.BOOKING_REQUEST__CAPACITY));
			bookingRequest.setDateIn(rs.getTimestamp(Fields.BOOKING_REQUEST__DATE_IN));
			bookingRequest.setDateOut(rs.getTimestamp(Fields.BOOKING_REQUEST__DATE_OUT));

			bookingRequest.setUser(new UserDAO().mapRow(rs));
			bookingRequest.setRoomClass(RoomClassDAO.getRoomClassSet
					(rs.getString(Fields.BOOKING_REQUEST__ROOM_CLASS_ID)));

			return bookingRequest;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
