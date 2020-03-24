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
			"SELECT * FROM booking_requests "
					+ "JOIN room_class ON booking_requests.room_class_id = room_class.id "
					+ "JOIN users ON booking_requests.user_id = users.id "
					+ "JOIN roles ON users.role_id = roles.id "
					+ "WHERE booking_requests.id=?";

	private static final String SQL__FIND_ALL_BOOKING_REQUESTS =
			"SELECT * FROM booking_requests "
					+ "JOIN room_class ON booking_requests.room_class_id = room_class.id "
					+ "JOIN users ON booking_requests.user_id = users.id "
					+ "JOIN roles ON users.role_id = roles.id";

	private static final String SQL__DELETE_BOOKING_REQUEST =
			"DELETE FROM booking_requests WHERE id=?";

	private static final String SQL__CREATE_BOOKING_REQUEST =
			"INSERT INTO booking_requests "
					+ "(user_id, date_in, date_out, capacity, room_class_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?, (SELECT id FROM room_class WHERE room_class_title=?))";


	/**
     * Returns booking request with given id.
     *
     * @param id
     *            Booking request entity identifier.
     * @return Booking request entity.
     */
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

	/**
     * Returns list of all booking requests.
     *
     * @return List of booking request entities.
     */
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

	/**
     * Delete booking request.
     *
     * @param booking request
     *            Booking request to delete.
     */
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

	/**
     * Create booking request.
     *
     * @param booking request
     *            Booking request to update.
     */
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

	/**
     * Create booking request.
     *
     * @param booking request
     *            Booking request to update.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
    private void createBookingRequest(Connection con, BookingRequest bookingRequest) throws SQLException {
    	PreparedStatement pstmt = con.prepareStatement(SQL__CREATE_BOOKING_REQUEST);
    	RoomClass roomClass = (RoomClass) bookingRequest.getRoomClass().toArray()[0];

		int k = 1;
		pstmt.setLong(k++, bookingRequest.getUser().getId());
		pstmt.setTimestamp(k++, bookingRequest.getDateIn());
		pstmt.setTimestamp(k++, bookingRequest.getDateOut());
		pstmt.setInt(k++, bookingRequest.getCapacity());
		pstmt.setString(k++, roomClass.getTitle());
		pstmt.executeUpdate();
		pstmt.close();
    }

	/**
     * Extracts booking request from the result set row.
     *
     * @param rs
     *        Result set row with data to extract.
     */
	@Override
	public BookingRequest mapRow(ResultSet rs) {
		try {
			BookingRequest bookingRequest = new BookingRequest();
			bookingRequest.setId(rs.getLong(Fields.ENTITY__ID));
			bookingRequest.setCapacity(rs.getInt(Fields.BOOKING_REQUEST__CAPACITY));
			bookingRequest.setDateIn(rs.getTimestamp(Fields.BOOKING_REQUEST__DATE_IN));
			bookingRequest.setDateOut(rs.getTimestamp(Fields.BOOKING_REQUEST__DATE_OUT));

			bookingRequest.setUser(new UserDAO().mapRow(rs));
			bookingRequest.setRoomClass(RoomClassDAO.getRoomClassSet(rs.getString(Fields.ROOM_CLASS__TITLE)));

			return bookingRequest;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
