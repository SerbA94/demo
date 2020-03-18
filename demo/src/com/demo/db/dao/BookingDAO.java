/**
 *
 */
package com.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.demo.db.DBManager;
import com.demo.db.constants.Fields;
import com.demo.db.entity.Booking;
import com.demo.db.entity.BookingStatus;
import com.demo.db.entity.Room;
import com.demo.db.entity.User;

/**
 * Data access object for Booking entity.
 *
 * @author A.Serbin
 */
public class BookingDAO implements EntityMapper<Booking> {

	private static final String SQL__FIND_ALL_BOOKINGS =
			"SELECT * FROM bookings "
					+ "JOIN booking_status ON bookings.booking_status_id = booking_status.id";

	private static final String SQL__FIND_BOOKING_BY_ID =
			"SELECT * FROM bookings "
					+ "JOIN booking_status ON bookings.booking_status_id = booking_status.id "
					+ "WHERE id=?";

	private static final String SQL__FIND_BOOKINGS_BY_USER_ID =
			"SELECT * FROM bookings "
					+ "JOIN booking_status ON bookings.booking_status_id = booking_status.id "
					+ "WHERE user_id=?";

	private static final String SQL__FIND_BOOKINGS_BY_ROOM_ID =
			"SELECT * FROM bookings "
					+ "JOIN booking_status ON bookings.booking_status_id = booking_status.id "
					+ "WHERE room_id=?";

	private static final String SQL__FIND_BOOKINGS_BY_DATE_IN =
			"SELECT * FROM bookings "
					+ "JOIN booking_status ON bookings.booking_status_id = booking_status.id "
					+ "WHERE date_in=?";

	private static final String SQL__FIND_BOOKINGS_BY_DATE_OUT =
			"SELECT * FROM bookings "
					+ "JOIN booking_status ON bookings.booking_status_id = booking_status.id "
					+ "WHERE date_out=?";

	private static final String SQL__FIND_BOOKINGS_BY_DATE_OF_BOOKING =
			"SELECT * FROM bookings "
					+ "JOIN booking_status ON bookings.booking_status_id = booking_status.id "
					+ "WHERE date_of_booking=?";

	private static final String SQL__FIND_BOOKINGS_BY_BOOKING_STATUS =
			"SELECT * FROM bookings "
					+ "WHERE booking_status_id "
						+ "IN (SELECT id FROM booking_status WHERE booking_status_title=?)";

	private static final String SQL__UPDATE_BOOKING =
			"UPDATE bookings SET "
					+ "booking_status_id=(SELECT id FROM booking_status WHERE booking_status_title=?) "
					+ "WHERE id=?";

	private static final String SQL__CREATE_BOOKING =
			"INSERT INTO bookings "
					+ "(date_in, date_out, date_of_booking, user_id, room_id, booking_status_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, (SELECT id FROM booking_status WHERE booking_status_title=?))";


    /**
     * Returns booking with given id
     *
     * @param id
     *     	Booking identifier.
     * @return Booking entity.
     */
	public Booking findBookingById(Long id) {
		Booking booking = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_BOOKING_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				booking = mapRow(rs);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			DBManager.getInstance().rollbackAndClose(con);
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return booking;
	}

	/**
     * Returns list of all bookings.
     *
     * @return List of booking entities.
     */
	public List<Booking> findAllBookings() {
		List<Booking> bookings = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL__FIND_ALL_BOOKINGS);
			while (rs.next()) {
				bookings.add(mapRow(rs));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return bookings;
	}

	/**
     * Returns bookings of the given date in.
     *
     * @param dateIn
     *            Timestamp of date in.
     * @return List of booking entities.
     */
	public List<Booking> findBookingsByDateIn(Timestamp dateIn) {
		List<Booking> bookings = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_BOOKINGS_BY_DATE_IN);
			pstmt.setTimestamp(1, dateIn);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookings.add(mapRow(rs));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return bookings;
	}

	/**
     * Returns bookings of the given date out.
     *
     * @param dateOut
     *            Timestamp of date out.
     * @return List of booking entities.
     */
	public List<Booking> findBookingsByDateOut(Timestamp dateOut) {
		List<Booking> bookings = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_BOOKINGS_BY_DATE_OUT);
			pstmt.setTimestamp(1, dateOut);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookings.add(mapRow(rs));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return bookings;
	}

	/**
     * Returns bookings of the given date of booking.
     *
     * @param dateOfBooking
     *            Timestamp of date of booking.
     * @return List of booking entities.
     */
	public List<Booking> findBookingsByDateOfBooking(Timestamp dateOfBooking) {
		List<Booking> bookings = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_BOOKINGS_BY_DATE_OF_BOOKING);
			pstmt.setTimestamp(1, dateOfBooking);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookings.add(mapRow(rs));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return bookings;
	}

	/**
     * Returns bookings of the given user.
     *
     * @param user
     *            User entity.
     * @return List of booking entities.
     */
	public List<Booking> findBookingsByUser(User user) {
		List<Booking> bookings = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_BOOKINGS_BY_USER_ID);
			pstmt.setLong(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookings.add(mapRow(rs));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return bookings;
	}

	/**
     * Returns bookings of the given room.
     *
     * @param room
     *            Room entity.
     * @return List of booking entities.
     */
	public List<Booking> findBookingsByRoom(Room room) {
		List<Booking> bookings = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_BOOKINGS_BY_ROOM_ID);
			pstmt.setLong(1, room.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookings.add(mapRow(rs));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return bookings;
	}

	/**
     * Returns bookings of the given booking status.
     *
     * @param bookingStatus
     *            BookingStatus enum.
     * @return List of booking entities.
     */
	public List<Booking> findBookingsByBookingStatus(BookingStatus bookingStatus) {
		List<Booking> bookings = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_BOOKINGS_BY_BOOKING_STATUS);
			pstmt.setString(1, bookingStatus.getTitle());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookings.add(mapRow(rs));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return bookings;
	}

	/**
     * Create booking.
     *
     * @param booking
     *            Booking to create.
     */
	public void createBooking(Booking booking) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			createBooking(con, booking);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	/**
     * Create booking.
     *
     * @param booking
     *            Booking to create.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
	private void createBooking(Connection con, Booking booking) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__CREATE_BOOKING);
		BookingStatus bookingStatus = (BookingStatus) booking.getBookingStatus().toArray()[0];

		int k = 1;
		pstmt.setTimestamp(k++, booking.getDateIn());
		pstmt.setTimestamp(k++, booking.getDateOut());
		pstmt.setTimestamp(k++, booking.getDateOfBooking());
		pstmt.setLong(k++, booking.getUser().getId());
		pstmt.setLong(k++, booking.getRoom().getId());
		pstmt.setString(k++, bookingStatus.getTitle());
		pstmt.executeUpdate();
		pstmt.close();
	}

    /**
     * Update booking.
     *
     * @param booking
     *            Booking to update.
     */
	public void updateBooking(Booking booking) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateBooking(con, booking);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	/**
     * Update booking.
     *
     * @param booking
     *            Booking to update.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
	private void updateBooking(Connection con, Booking booking) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__UPDATE_BOOKING);
		BookingStatus bookingStatus = (BookingStatus) booking.getBookingStatus().toArray()[0];

		int k = 1;
		pstmt.setString(k++, bookingStatus.getTitle());
		pstmt.setLong(k, booking.getId());
		pstmt.executeUpdate();
		pstmt.close();
	}

	/**
     * Extracts booking from the result set row.
     *
     * @param rs
     *        Result set row with data to extract.
     */
	@Override
	public Booking mapRow(ResultSet rs) {
		try {
			Booking booking = new Booking();
			booking.setId(rs.getLong(Fields.ENTITY__ID));
			booking.setDateIn(rs.getTimestamp(Fields.BOOKING__DATE_IN));
			booking.setDateOut(rs.getTimestamp(Fields.BOOKING__DATE_OUT));
			booking.setDateOfBooking(rs.getTimestamp(Fields.BOOKING__DATE_OF_BOOKING));
			booking.setBookingStatus(BookingStatusDAO.getBookingStatusSet(
					rs.getString(Fields.BOOKING_STATUS__TITLE)));

			booking.setRoom(new RoomDAO().mapRow(rs));
			booking.setUser(new UserDAO().mapRow(rs));

			return booking;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
