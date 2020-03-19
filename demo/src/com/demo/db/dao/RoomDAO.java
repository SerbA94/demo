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
import com.demo.db.entity.Description;
import com.demo.db.entity.Room;
import com.demo.db.entity.RoomClass;
import com.demo.db.entity.RoomStatus;

/**
 * Data access object for Room entity.
 *
 * @author A.Serbin
 */
public class RoomDAO implements EntityMapper<Room>{

	private static final String SQL__FIND_ROOM_BY_ID =
			"SELECT * FROM rooms "
					+ "JOIN room_class ON rooms.room_class_id = room_class.id "
					+ "JOIN room_status ON rooms.room_status_id = room_status.id "
					+ "WHERE id=?";

	private static final String SQL__FIND_ROOM_BY_NUMBER =
			"SELECT * FROM rooms "
					+ "JOIN room_class ON rooms.room_class_id = room_class.id "
					+ "JOIN room_status ON rooms.room_status_id = room_status.id "
					+ "WHERE number=?";

	private static final String SQL__FIND_ALL_ROOMS =
			"SELECT * FROM rooms "
					+ "JOIN room_class ON rooms.room_class_id = room_class.id "
					+ "JOIN room_status ON rooms.room_status_id = room_status.id";

	private static final String SQL__FIND_ROOMS_BY_PRICE_BETWEEN =
			"SELECT * FROM rooms "
					+ "JOIN room_class ON rooms.room_class_id = room_class.id "
					+ "JOIN room_status ON rooms.room_status_id = room_status.id "
					+ "WHERE price BETWEEN ? AND ?";

	private static final String SQL__FIND_ROOMS_BY_STATUS =
			"SELECT * FROM rooms "
					+ "JOIN room_class ON rooms.room_class_id = room_class.id "
					+ "WHERE room_status_id "
						+ "IN (SELECT id FROM room_status WHERE room_status_title=?)";

	private static final String SQL__FIND_ROOMS_BY_CLASS =
			"SELECT * FROM rooms "
					+ "JOIN room_status ON rooms.room_status_id = room_status.id "
					+ "WHERE room_class_id "
						+ "IN (SELECT id FROM room_class WHERE room_class_title=?)";

	private static final String SQL__CREATE_ROOM =
			"INSERT INTO rooms "
					+ "(number, capacity, price, room_class_id, room_status_id) "
					+ "VALUES "
					+ "(?, ?, ?, "
						+ "(SELECT id FROM room_class WHERE room_class_title=?), "
						+ "(SELECT id FROM room_status WHERE room_status_title=?))";

	private static final String SQL__UPDATE_ROOM =
			"UPDATE rooms "
					+ "SET capacity=?, "
						+ "price=?, "
						+ "room_class_id=(SELECT id FROM room_class WHERE room_class_title=?), "
						+ "room_status_id=(SELECT id FROM room_status WHERE room_status_title=?) "
					+ "WHERE id=?";

	private static final String SQL__CREATE_DESCRIPTION =
			"INSERT INTO descriptions (locale_name, description, room_id) VALUES (?, ?, ?)";

	private static final String SQL__UPDATE_DESCRIPTION =
			"UPDATE descriptions SET locale_name=?, description=? WHERE id=?";

	private static final String SQL__FIND_ROOM_DESCRIPTIONS =
			"SELECT * FROM descriptions WHERE room_id=?";



    /**
     * Returns room with given id
     *
     * @param id
     *     	Room identifier.
     * @return Room entity.
     */
	public Room findRoomById(Long id) {
		Room room = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_ROOM_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				room = mapRow(rs);
			}
			rs.close();
			pstmt.close();
			room.setDescriptions(findRoomDescriptions(con));
		} catch (SQLException ex) {
			ex.printStackTrace();
			DBManager.getInstance().rollbackAndClose(con);
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return room;
	}

	/**
     * Returns room with given number
     *
     * @param number
     *     	Room number.
     * @return Room entity.
     */
	public Room findRoomByNumber(Integer number) {
		Room room = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_ROOM_BY_NUMBER);
			pstmt.setLong(1, number);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				room = mapRow(rs);
			}
			rs.close();
			pstmt.close();
			room.setDescriptions(findRoomDescriptions(con));
		} catch (SQLException ex) {
			ex.printStackTrace();
			DBManager.getInstance().rollbackAndClose(con);
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return room;
	}

	/**
     * Returns list of all rooms.
     *
     * @return List of room entities.
     */
	public List<Room> findAllRooms() {
		List<Room> rooms = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL__FIND_ALL_ROOMS);
			while (rs.next()) {
				Room room = mapRow(rs);
				room.setDescriptions(findRoomDescriptions(con));
				rooms.add(room);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rooms;
	}

	/**
     * Returns rooms with price range between lowestPrice and highestPrice
     *
     * @param lowestPrice
     *     	Start of price range.
     * @param highestPrice
     *      End of price range.
     * @return List of room entities.
     */
	public List<Room> findRoomsByPriceBetween(Integer lowestPrice,Integer highestPrice) {
		List<Room> rooms = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_ROOMS_BY_PRICE_BETWEEN);

			if(lowestPrice < highestPrice) {
				pstmt.setInt(1, lowestPrice);
				pstmt.setInt(2, highestPrice);
			}else {
				pstmt.setInt(1, highestPrice);
				pstmt.setInt(2, lowestPrice);
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Room room = mapRow(rs);
				room.setDescriptions(findRoomDescriptions(con));
				rooms.add(room);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return rooms;
	}

	/**
     * Returns rooms with given room status
     *
     * @param roomStatus
     *     	Room status enum.
     * @return List of room entities.
     */
	public List<Room> findRoomsByStatus(RoomStatus roomStatus) {
		List<Room> rooms = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_ROOMS_BY_STATUS);
			pstmt.setString(1, roomStatus.getTitle());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Room room = mapRow(rs);
				room.setDescriptions(findRoomDescriptions(con));
				rooms.add(room);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return rooms;
	}

	/**
     * Returns rooms with given room class
     *
     * @param roomClass
     *     	Room class enum.
     * @return List of room entities.
     */
	public List<Room> findRoomsByClass(RoomClass roomClass) {
		List<Room> rooms = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_ROOMS_BY_CLASS);
			pstmt.setString(1, roomClass.getTitle());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Room room = mapRow(rs);
				room.setDescriptions(findRoomDescriptions(con));
				rooms.add(room);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return rooms;
	}

	/**
     * Update room.
     *
     * @param room
     *            Room to update.
     */
	public void updateRoom(Room room) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			List<Description> descriptions = room.getDescriptions();
			for (Description description : descriptions) {
				updateDescription(con,description);
			}
			updateRoom(con, room);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	/**
     * Update room.
     *
     * @param room
     *            Room to update.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
    private void updateRoom(Connection con, Room room) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__UPDATE_ROOM);
		RoomClass roomClass = (RoomClass) room.getRoomClass().toArray()[0];
		RoomStatus roomStatus = (RoomStatus) room.getRoomStatus().toArray()[0];

		int k = 1;
		pstmt.setInt(k++, room.getCapacity());
		pstmt.setInt(k++, room.getPrice());
		pstmt.setString(k++, roomClass.getTitle());
		pstmt.setString(k++, roomStatus.getTitle());
		pstmt.setLong(k, room.getId());
		pstmt.executeUpdate();
		pstmt.close();
    }

	/**
     * Update description.
     *
     * @param description
     *            Description to update.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
    private void updateDescription(Connection con, Description description) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__UPDATE_DESCRIPTION);
		int k = 1;
		pstmt.setString(k++, description.getLocaleName());
		pstmt.setString(k++, description.getDescription());
		pstmt.setLong(k, description.getId());
		pstmt.executeUpdate();
		pstmt.close();
    }

	/**
     * Create room.
     *
     * @param room
     *            Room to create.
     */
	public void createRoom(Room room) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			List<Description> descriptions = room.getDescriptions();
			Long roomId = createRoom(con, room);
			for (Description description : descriptions) {
				description.setRoomId(roomId);
				createDescription(con,description);
			}
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	/**
     * Create room.
     *
     * @param room
     *            Room to create.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
    private Long createRoom(Connection con, Room room) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__CREATE_ROOM, Statement.RETURN_GENERATED_KEYS);
		RoomClass roomClass = (RoomClass) room.getRoomClass().toArray()[0];
		RoomStatus roomStatus = (RoomStatus) room.getRoomStatus().toArray()[0];

		Long id = null;

		int k = 1;
		pstmt.setInt(k++, room.getNumber());
		pstmt.setInt(k++, room.getCapacity());
		pstmt.setInt(k++, room.getPrice());
		pstmt.setString(k++, roomClass.getTitle());
		pstmt.setString(k++, roomStatus.getTitle());
		pstmt.executeUpdate();

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }finally{
        	pstmt.close();
        }
        return id;
    }

	/**
     * Create description.
     *
     * @param description
     *            Description to create.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
    private void createDescription(Connection con, Description description) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__CREATE_DESCRIPTION);
		int k = 1;
		pstmt.setString(k++, description.getLocaleName());
		pstmt.setString(k++, description.getDescription());
		pstmt.setLong(k++, description.getRoomId());
		pstmt.executeUpdate();
		pstmt.close();
    }

	/**
     * Returns list of all room descriptions.
     *
     * @return List of room descriptions.
     *
	 * @throws SQLException
     */
	private List<Description> findRoomDescriptions(Connection con) throws SQLException {
		List<Description> descriptions = new ArrayList<>();
		Statement stmt = con.createStatement();;
		ResultSet rs = stmt.executeQuery(SQL__FIND_ROOM_DESCRIPTIONS);;

			while (rs.next()) {
				descriptions.add(new DescriptionMapper().mapRow(rs));
			}
			rs.close();
			stmt.close();

		return descriptions;
	}

    /**
     * Extracts room from the result set row.
     *
     * @param rs
     *        Result set row with data to extract.
     */
	@Override
	public Room mapRow(ResultSet rs) {
		try {
			Room room = new Room();
			room.setId(rs.getLong(Fields.ENTITY__ID));
			room.setNumber(rs.getInt(Fields.ROOM__NUMBER));
			room.setCapacity(rs.getInt(Fields.ROOM__CAPACITY));
			room.setPrice(rs.getInt(Fields.ROOM__PRICE));
			room.setRoomClass(RoomClassDAO.getRoomClassSet(rs.getString(Fields.ROOM_CLASS__TITLE)));
			room.setRoomStatus(RoomStatusDAO.getRoomStatusSet(rs.getString(Fields.ROOM_STATUS__TITLE)));
			return room;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

    /**
     * Extracts description from the result set row.
     */
    private static class DescriptionMapper implements EntityMapper<Description> {

        @Override
        public Description mapRow(ResultSet rs) {
            try {
            	Description description = new Description();
            	description.setId(rs.getLong(Fields.ENTITY__ID));
            	description.setLocaleName(rs.getString(Fields.DESCRIPTION__LOCALE_NAME));
            	description.setDescription(rs.getString(Fields.DESCRIPTION__DESCRIPTION));
            	description.setRoomId(rs.getLong(Fields.DESCRIPTION__ROOM_ID));
                return description;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }


}