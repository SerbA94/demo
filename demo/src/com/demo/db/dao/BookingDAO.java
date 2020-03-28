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
import java.util.Collections;
import java.util.List;

import com.demo.db.DBManager;
import com.demo.db.constants.Fields;
import com.demo.db.entity.Booking;
import com.demo.db.entity.BookingStatus;
import com.demo.db.entity.Image;
import com.demo.db.entity.Room;
import com.demo.db.entity.RoomStatus;
import com.demo.db.entity.User;

/**
 * Data access object for Booking entity.
 *
 * @author A.Serbin
 */
public class BookingDAO implements EntityMapper<Booking> {

	private static final String SELECT_ALL_WITH_JOINS =
			"SELECT * FROM bookings "
					+ "JOIN booking_status ON bookings.booking_status_id = booking_status.booking_status_id "
					+ "JOIN users ON bookings.user_id = users.user_id "
					+ "JOIN rooms ON bookings.room_id = rooms.room_id "
					+ "JOIN roles ON users.role_id = roles.role_id "
					+ "JOIN room_class ON rooms.room_class_id = room_class.room_class_id "
					+ "JOIN room_status ON rooms.room_status_id = room_status.room_status_id";

	private static final String SQL__FIND_ALL_BOOKINGS = SELECT_ALL_WITH_JOINS;

	private static final String SQL__FIND_BOOKING_BY_ID = SELECT_ALL_WITH_JOINS
					+ " WHERE bookings.booking_id=?";

	private static final String SQL__FIND_BOOKINGS_BY_USER_ID = SELECT_ALL_WITH_JOINS
					+ " WHERE bookings.user_id=?";

	private static final String SQL__FIND_ACTIVE_BOOKINGS_BY_USER_ID = SELECT_ALL_WITH_JOINS
			+ " WHERE bookings.user_id=? AND bookings.booking_status_id IN "
				+ "(SELECT booking_status.booking_status_id FROM booking_status "
					+ "WHERE booking_status.booking_status_title='active')";

	private static final String SQL__FIND_HANDLING_BOOKINGS_BY_USER_ID = SELECT_ALL_WITH_JOINS
			+ " WHERE bookings.user_id=?"
			+ " AND bookings.booking_status_id IN ("
				+ "SELECT booking_status.booking_status_id "
			    + "FROM booking_status "
				+ "WHERE booking_status.booking_status_title IN ('not paid','unconfirmed'))";

	private static final String SQL__FIND_BOOKINGS_BY_ROOM_ID = SELECT_ALL_WITH_JOINS
					+ " WHERE bookings.room_id=?";

	private static final String SQL__FIND_BOOKINGS_BY_DATE_IN = SELECT_ALL_WITH_JOINS
					+ " WHERE bookings.date_in=?";

	private static final String SQL__FIND_BOOKINGS_BY_DATE_OUT = SELECT_ALL_WITH_JOINS
					+ " WHERE bookings.date_out=?";

	private static final String SQL__FIND_BOOKINGS_BY_DATE_OF_BOOKING = SELECT_ALL_WITH_JOINS
					+ " WHERE bookings.date_of_booking=?";

	private static final String SQL__FIND_BOOKINGS_BY_BOOKING_STATUS =
			"SELECT * FROM bookings "
					+ "WHERE bookings.booking_status_id "
						+ "IN (SELECT booking_status.booking_status_id FROM booking_status "
							+ "WHERE booking_status.booking_status_title=?)";

	private static final String SQL__UPDATE_BOOKING =
			"UPDATE bookings SET "
					+ "bookings.booking_status_id=(SELECT booking_status.booking_status_id "
						+ "FROM booking_status WHERE booking_status.booking_status_title=?) "
					+ "WHERE bookings.booking_id=?";

	private static final String SQL__CREATE_BOOKING =
			"INSERT INTO bookings "
					+ "(bookings.date_in, bookings.date_out, bookings.date_of_booking, "
						+ "bookings.user_id, bookings.room_id, bookings.booking_status_id) "
					+ "VALUES (?, ?, ?, ?, ?, "
						+ "(SELECT booking_status.booking_status_id FROM booking_status "
							+ "WHERE booking_status.booking_status_title=?))";

	private static final String SQL__DELETE_BOOKING =
			"DELETE FROM bookings WHERE bookings.booking_id=?";


    /**
     * Returns booking with given id
     *
     * @param id
     *     	Booking identifier.
     * @return Booking entity.
     * @throws SQLException
     */
	public Booking findBookingById(Long id){
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
				Booking booking = mapRow(rs);
				Room room = booking.getRoom();
				List<Image> images = new ImageDAO().findRoomImages(booking.getRoom());
				room.setImages(images);
				booking.setRoom(room);
				bookings.add(booking);
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
     * Returns active bookings of the given user.
     *
     * @param user
     *            User entity.
     * @return List of booking entities.
     */
	public List<Booking> findActiveBookingsByUser(User user) {
		List<Booking> bookings = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_ACTIVE_BOOKINGS_BY_USER_ID);
			pstmt.setLong(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Booking booking = mapRow(rs);
				Room room = booking.getRoom();
				List<Image> images = new ImageDAO().findRoomImages(booking.getRoom());
				room.setImages(images);
				booking.setRoom(room);
				bookings.add(booking);
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
     * Returns handling bookings of the given user.
     *
     * @param user
     *            User entity.
     * @return List of booking entities.
     */
	public List<Booking> findHandlingBookingsByUser(User user) {
		List<Booking> bookings = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_HANDLING_BOOKINGS_BY_USER_ID);
			pstmt.setLong(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Booking booking = mapRow(rs);
				Room room = booking.getRoom();
				List<Image> images = new ImageDAO().findRoomImages(booking.getRoom());
				room.setImages(images);
				booking.setRoom(room);
				bookings.add(booking);
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
     * @return booking entity
     * 			  Booking with seted generated id
     */
	public Booking createBooking(Booking booking) {
		Connection con = null;
		Booking bookingReturn = null;
		Room room = booking.getRoom();
		room.setRoomStatus(Collections.singleton(RoomStatus.BOOKED));
		try {
			con = DBManager.getInstance().getConnection();
			booking = createBooking(con, booking);
			RoomDAO.updateRoom(con, room);
			createBookingDeleteEvent(con,booking);
			createRoomUpdateEvent(con,booking);
			bookingReturn = booking;
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return bookingReturn;
	}

	/**
     * Create booking.
     *
     * @param booking
     *            Booking to create.
     * @param con
     *            Connection to db.
     * @return booking entity
     * 			  Booking with seted generated id
     * @throws SQLException
     */
	private Booking createBooking(Connection con, Booking booking) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__CREATE_BOOKING,Statement.RETURN_GENERATED_KEYS);
		BookingStatus bookingStatus = (BookingStatus) booking.getBookingStatus().toArray()[0];

		int k = 1;
		pstmt.setTimestamp(k++, booking.getDateIn());
		pstmt.setTimestamp(k++, booking.getDateOut());
		pstmt.setTimestamp(k++, booking.getDateOfBooking());
		pstmt.setLong(k++, booking.getUser().getId());
		pstmt.setLong(k++, booking.getRoom().getId());
		pstmt.setString(k++, bookingStatus.getTitle());
		pstmt.executeUpdate();

		Long id = null;
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating room failed, no ID obtained.");
            }
        }finally{
        	pstmt.close();
        }
        booking.setId(id);
        return booking;
	}

    /**
     * Update booking.
     *
     * @param booking
     *            Booking to update.
     * @throws SQLException
     */
	public void updateBooking(Booking booking){
		BookingStatus bookingStatus = (BookingStatus) booking.getBookingStatus().toArray()[0];
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateBooking(con, booking);
			if(bookingStatus.equals(BookingStatus.ACTIVE) || bookingStatus.equals(BookingStatus.EXPIRED)) {
				dropBookingEvents(con,booking);
			}
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
     * Delete booking.
     *
     * @param booking
     *            Booking to delete.
	 * @throws SQLException
     */
	public void deleteBooking(Booking booking) {
		PreparedStatement pstmt = null;
		Connection con = null;
		Room room = booking.getRoom();
		room.setRoomStatus(Collections.singleton(RoomStatus.FREE));
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__DELETE_BOOKING);
			pstmt.setLong(1, booking.getId());
			pstmt.execute();
			pstmt.close();
			RoomDAO.updateRoom(con, room);
			dropBookingEvents(con, booking);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	// before deploy change to : INTERVAL 2 DAYS
	/**
     * Create booking delete event.
     *
     * @param booking
     *            Booking to delete.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
	private void createBookingDeleteEvent(Connection con, Booking booking) throws SQLException {
		Statement stmt = con.createStatement();

		String sqlRequest =
				"CREATE EVENT delete_booking_" + booking.getId() + "_event "
					+ "ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 5 MINUTE "
					+ "ON COMPLETION PRESERVE "
					+ "DO "
					+ "DELETE FROM bookings WHERE bookings.booking_id=" + booking.getId() + ";";

		stmt.execute(sqlRequest);
		stmt.close();
	}

	// before deploy change to : INTERVAL 2 DAYS
	/**
     * Create room update event.
     *
     * @param booking
     *            Booking room to update.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
	private void createRoomUpdateEvent(Connection con, Booking booking) throws SQLException {
		Statement stmt = con.createStatement();

		String sqlRequest =
				"CREATE EVENT update_booking_" + booking.getId() + "_room_event "
					+ "ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 5 MINUTE "
					+ "ON COMPLETION PRESERVE "
					+ "DO "
					+ "UPDATE rooms "
						+ "SET rooms.room_status_id=("
							+ "SELECT room_status.room_status_id "
							+ "FROM room_status "
							+ "WHERE room_status.room_status_title='free') "
						+ "WHERE rooms.room_id='" + booking.getRoom().getId() + "';";

		stmt.execute(sqlRequest);
		stmt.close();
	}

	/**
     * Drops event associated with booking.
     *
     * @param booking
     *            Booking of event to drop.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
	private void dropBookingEvents(Connection con, Booking booking) throws SQLException {
		Statement stmt = con.createStatement();

		String bookingDeleteEvent = "DROP EVENT IF EXISTS delete_booking_" + booking.getId() + "_event;";
		String roomUpdateEvent = "DROP EVENT IF EXISTS update_booking_" + booking.getId() + "_room_event;";

		stmt.execute(bookingDeleteEvent);
		stmt.execute(roomUpdateEvent);
		stmt.close();
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
			booking.setId(rs.getLong(Fields.BOOKING__BOOKING_ID));
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
