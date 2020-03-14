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
			"SELECT * FROM rooms WHERE id=?";
	private static final String SQL__FIND_ROOM_BY_NUMBER =
			"SELECT * FROM rooms WHERE number=?";
	private static final String SQL__FIND_ALL_ROOMS =
			"SELECT * FROM rooms";
	private static final String SQL__FIND_ROOMS_BY_PRICE_BETWEEN =
			"SELECT * FROM rooms WHERE price BETWEEN ? AND ?";
	private static final String SQL__FIND_ROOMS_BY_STATUS =
			"SELECT * FROM rooms WHERE room_status_id=?";
	private static final String SQL__FIND_ROOMS_BY_CLASS =
			"SELECT * FROM rooms WHERE room_class_id=?";
	private static final String SQL__CREATE_ROOM =
			"INSERT INTO rooms (number, capacity, price, description,"
			+ " room_class_id, room_status_id) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL__UPDATE_ROOM =
			"UPDATE rooms SET capacity=?, price=?, description=?, "
			+ "room_class_id=?, room_status_id=? WHERE id=?";


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
		} catch (SQLException ex) {
			ex.printStackTrace();
			DBManager.getInstance().rollbackAndClose(con);
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return room;
	}

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
		} catch (SQLException ex) {
			ex.printStackTrace();
			DBManager.getInstance().rollbackAndClose(con);
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return room;
	}

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
				rooms.add(mapRow(rs));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rooms;
	}

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
				rooms.add(mapRow(rs));
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

	public List<Room> findRoomsByStatus(RoomStatus roomStatus) {
		List<Room> rooms = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_ROOMS_BY_STATUS);
			pstmt.setLong(1, new RoomStatusDAO().findRoomStatusId(roomStatus));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rooms.add(mapRow(rs));
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

	public List<Room> findRoomsByClass(RoomClass roomClass) {
		List<Room> rooms = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_ROOMS_BY_CLASS);
			pstmt.setLong(1, new RoomClassDAO().findRoomClassId(roomClass));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rooms.add(mapRow(rs));
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

	public void createRoom(Room room) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			createRoom(con, room);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public void updateRoom(Room room) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateRoom(con, room);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

    private void updateRoom(Connection con, Room room) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__UPDATE_ROOM);
		Long roomClassId = new RoomClassDAO()
				.findRoomClassId((RoomClass) room.getRoomClass().toArray()[0]);
		Long roomStatusId = new RoomStatusDAO()
				.findRoomStatusId((RoomStatus) room.getRoomStatus().toArray()[0]);

		int k = 1;
		pstmt.setInt(k++, room.getCapacity());
		pstmt.setInt(k++, room.getPrice());
		pstmt.setString(k++, room.getDescription());
		pstmt.setLong(k++, roomClassId);
		pstmt.setLong(k++, roomStatusId);
		pstmt.setLong(k, room.getId());
		pstmt.executeUpdate();
		pstmt.close();
    }

    private void createRoom(Connection con, Room room) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__CREATE_ROOM);
		Long roomClassId = new RoomClassDAO()
				.findRoomClassId((RoomClass) room.getRoomClass().toArray()[0]);
		Long roomStatusId = new RoomStatusDAO()
				.findRoomStatusId((RoomStatus) room.getRoomStatus().toArray()[0]);

		int k = 1;
		pstmt.setInt(k++, room.getNumber());
		pstmt.setInt(k++, room.getCapacity());
		pstmt.setInt(k++, room.getPrice());
		pstmt.setString(k++, room.getDescription());
		pstmt.setLong(k++, roomClassId);
		pstmt.setLong(k++, roomStatusId);
		pstmt.executeUpdate();
		pstmt.close();
    }

	@Override
	public Room mapRow(ResultSet rs) {
		try {
			Room room = new Room();
			room.setId(rs.getLong(Fields.ENTITY__ID));
			room.setNumber(rs.getInt(Fields.ROOM__NUMBER));
			room.setCapacity(rs.getInt(Fields.ROOM__CAPACITY));
			room.setPrice(rs.getInt(Fields.ROOM__PRICE));
			room.setDescription(rs.getString(Fields.ROOM__DESCRIPTION));
			room.setRoomClass(RoomClassDAO.getRoomClassSet(rs.getString(Fields.ROOM__ROOM_CLASS_ID)));
			room.setRoomStatus(RoomStatusDAO.getRoomStatusSet(rs.getString(Fields.ROOM__ROOM_STATUS_ID)));
			return room;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
